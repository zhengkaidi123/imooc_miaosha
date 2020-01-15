package swjtu.zkd.miaosha.dao;

import org.apache.ibatis.annotations.*;
import swjtu.zkd.miaosha.domain.MiaoshaOrder;
import swjtu.zkd.miaosha.domain.OrderInfo;

@Mapper
public interface OrderDAO {

    String ORDER_TABLE_NAME = "order_info";

    String MIAOSHA_ORDER_TABLE_NAME = "miaosha_order";

    String MIAOSHA_SELECT_FIELDS = " id, user_id, order_id, goods_id ";

    String ORDER_INSERT_FIELDS = " user_id, goods_id, delivery_addr_id, goods_name, goods_count, goods_price, order_channel, status, create_date, pay_date ";

    String MIAOSHA_INSERT_FIELDS = " user_id, order_id, goods_id ";

    @Select({"select ", MIAOSHA_SELECT_FIELDS, "from ", MIAOSHA_ORDER_TABLE_NAME, " where user_id=#{userId} and goods_id=#{goodsId}"})
    MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert({"insert into ", ORDER_TABLE_NAME, " (", ORDER_INSERT_FIELDS, ") values ( #{userId}, #{goodsId}, #{deliveryAddrId}, #{goodsName}, " +
            "#{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate}, #{payDate})"})
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Insert({"insert into ", MIAOSHA_ORDER_TABLE_NAME, " (", MIAOSHA_INSERT_FIELDS, ") values (#{userId}, #{goodsId}, #{orderId})"})
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
