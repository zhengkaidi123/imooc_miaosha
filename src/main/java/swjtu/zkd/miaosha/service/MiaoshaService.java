package swjtu.zkd.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.domain.OrderInfo;
import swjtu.zkd.miaosha.vo.GoodsVO;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        goodsService.reduceStock(goods);
        OrderInfo orderInfo = orderService.createOrder(user, goods);
        return orderInfo;
    }
}
