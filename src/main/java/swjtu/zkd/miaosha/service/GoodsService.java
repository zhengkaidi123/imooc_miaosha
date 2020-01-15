package swjtu.zkd.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swjtu.zkd.miaosha.dao.GoodsDAO;
import swjtu.zkd.miaosha.domain.MiaoshaGoods;
import swjtu.zkd.miaosha.vo.GoodsVO;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    public List<GoodsVO> listGoodsVO() {
        return goodsDAO.listGoodsVO();
    }

    public GoodsVO getGoodsVOByGoodsId(long goodsId) {
        return goodsDAO.getGoodsVOByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVO goods) {
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goods.getId());
        goodsDAO.reduceStock(miaoshaGoods);
    }
}
