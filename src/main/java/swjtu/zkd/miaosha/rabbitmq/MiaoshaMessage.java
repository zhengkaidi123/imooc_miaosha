package swjtu.zkd.miaosha.rabbitmq;

import lombok.Data;
import swjtu.zkd.miaosha.domain.MiaoshaUser;

@Data
public class MiaoshaMessage {

    private MiaoshaUser miaoshaUser;

    private long goodsId;
}
