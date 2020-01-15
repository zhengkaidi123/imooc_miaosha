package swjtu.zkd.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swjtu.zkd.miaosha.dao.OrderDAO;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.vo.GoodsVO;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return orderDAO.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(OrderInfo.OrderStatus.NOT_PAY.getStatus());
        orderInfo.setUserId(user.getId());
        long orderId = orderDAO.insertOrder(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDAO.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
