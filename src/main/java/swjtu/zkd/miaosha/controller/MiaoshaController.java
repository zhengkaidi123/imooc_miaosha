package swjtu.zkd.miaosha.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.rabbitmq.MQSender;
import swjtu.zkd.miaosha.rabbitmq.MiaoshaMessage;
import swjtu.zkd.miaosha.redis.GoodsKey;
import swjtu.zkd.miaosha.redis.RedisService;
import swjtu.zkd.miaosha.result.CodeMsg;
import swjtu.zkd.miaosha.result.Result;
import swjtu.zkd.miaosha.service.GoodsService;
import swjtu.zkd.miaosha.service.MiaoshaService;
import swjtu.zkd.miaosha.service.OrderService;
import swjtu.zkd.miaosha.vo.GoodsVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /*
        系统初始化
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        if (goodsList != null) {
            for (GoodsVO goodsVO : goodsList) {
                redisService.set(GoodsKey.miaoshaGoodsStockPrefix, String.valueOf(goodsVO.getId()), goodsVO.getStockCount());
                localOverMap.put(goodsVO.getId(), false);
            }
        }
    }

    /*
        get和post有什么区别？
        get是幂等的，无论调用多少次，结果相同，不会对服务端数据产生影响
        post是非幂等的
         */
    @PostMapping("/do_miaosha")
    @ResponseBody
    public Result<Integer> list(@RequestParam("goodsId") long goodsId, MiaoshaUser user) {
        // 内存标记，减少Redis访问
        boolean miaoshaOver = localOverMap.get(goodsId);
        if (miaoshaOver) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.miaoshaGoodsStockPrefix, String.valueOf(goodsId));
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATED_MIAOSHA);
        }
        // 入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setMiaoshaUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(miaoshaMessage);
        // 排队中
        return Result.success(0);

        /*
        //判断库存
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATED_MIAOSHA);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
         */
    }

    /*
        orderId : 成功
        0  :  排队中
        -1 :  秒杀失败
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> miaoshaResult(@RequestParam("goodsId") long goodsId, MiaoshaUser user) {
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }
}
