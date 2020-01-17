package swjtu.zkd.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.redis.MiaoshaKey;
import swjtu.zkd.miaosha.redis.RedisService;
import swjtu.zkd.miaosha.vo.GoodsVO;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        boolean success = goodsService.reduceStock(goods);
        OrderInfo orderInfo = null;
        if (success) {
            orderInfo = orderService.createOrder(user, goods);
        } else {
            setMiaoshaGoodsOver(goods.getId());
        }
        return orderInfo;
    }

    private void setMiaoshaGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.goodsOver, String.valueOf(goodsId), true);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
        if (miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean isOver = getMiaoshaGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getMiaoshaGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.goodsOver, String.valueOf(goodsId));
    }
}
