package swjtu.zkd.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.result.CodeMsg;
import swjtu.zkd.miaosha.result.Result;
import swjtu.zkd.miaosha.service.GoodsService;
import swjtu.zkd.miaosha.service.MiaoshaService;
import swjtu.zkd.miaosha.service.OrderService;
import swjtu.zkd.miaosha.vo.GoodsVO;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    /*
    get和post有什么区别？
    get是幂等的，无论调用多少次，结果相同，不会对服务端数据产生影响
    post是非幂等的
     */
    @PostMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> list(@RequestParam("goodsId") long goodsId, Model model, MiaoshaUser user) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
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
    }
}
