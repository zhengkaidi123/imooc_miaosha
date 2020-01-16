package swjtu.zkd.miaosha.vo;

import lombok.Data;
import swjtu.zkd.miaosha.domain.OrderInfo;

@Data
public class OrderDetailVO {

    private GoodsVO goodsVO;

    private OrderInfo orderInfo;
}
