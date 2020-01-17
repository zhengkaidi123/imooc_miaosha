package swjtu.zkd.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

    public static MiaoshaKey goodsOver = new MiaoshaKey("goodsOver");

    public MiaoshaKey(String prefix) {
        super(prefix);
    }
}
