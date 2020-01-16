package swjtu.zkd.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import swjtu.zkd.miaosha.domain.MiaoshaGoods;
import swjtu.zkd.miaosha.vo.GoodsVO;

import java.util.List;

@Mapper
public interface GoodsDAO {

    String TABLE_NAME = "goods";

    String SELECT_FIELDS = " id,goods_name,goods_title,goods_img,goods_detail,goods_price,goods_stock ";

    @Select({"select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id"})
    List<GoodsVO> listGoodsVO();

    @Select({"select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where mg.goods_id = #{goodsId}"})
    GoodsVO getGoodsVOByGoodsId(@Param("goodsId") long goodsId);

    @Update({"update miaosha_goods set stock_count = stock_count - 1 where goods_id=#{goodsId} and stock_count>0"})
    int reduceStock(MiaoshaGoods miaoshaGoods);
}
