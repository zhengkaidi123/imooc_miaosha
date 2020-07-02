package swjtu.zkd.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

    public static MiaoshaKey goodsOver = new MiaoshaKey("goodsOver");

    public static MiaoshaKey miaoshaPath = new MiaoshaKey(60, "miaoshaPath");
    public static KeyPrefix verifyCode = new MiaoshaKey(300, "verifyCode");

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
