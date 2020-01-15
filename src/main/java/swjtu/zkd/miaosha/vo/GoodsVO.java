package swjtu.zkd.miaosha.vo;

import lombok.Data;
import swjtu.zkd.miaosha.domain.Goods;

import java.util.Date;

@Data
public class GoodsVO extends Goods {

    private Double miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}
