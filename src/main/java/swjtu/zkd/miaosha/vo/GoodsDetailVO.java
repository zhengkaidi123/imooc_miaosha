package swjtu.zkd.miaosha.vo;

import lombok.Data;
import swjtu.zkd.miaosha.domain.MiaoshaUser;

@Data
public class GoodsDetailVO {

    private GoodsVO goodsVO;

    private MiaoshaUser user;

    private int miaoshaStaus;

    private int remainSeconds;
}
