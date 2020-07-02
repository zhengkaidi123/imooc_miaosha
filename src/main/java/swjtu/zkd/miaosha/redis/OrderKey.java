package swjtu.zkd.miaosha.redis;

public class OrderKey extends BasePrefix {

    public static OrderKey orderPrefix = new OrderKey(0, "order");

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
