package swjtu.zkd.miaosha.redis;

public class OrderKey extends BasePrefix {

    public static OrderKey OrderPrefix = new OrderKey(300, "order");

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
