package swjtu.zkd.miaosha.redis;

public class GoodsKey extends BasePrefix {

    public static GoodsKey goodsListPrefix = new GoodsKey(60,"goodsList");

    public static KeyPrefix goodsDetailPrefix = new GoodsKey(60, "goodsDetail");

    public static KeyPrefix miaoshaGoodsStockPrefix = new GoodsKey(0, "miaoshaGoodsStock");

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
