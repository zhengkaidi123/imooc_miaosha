package swjtu.zkd.miaosha.redis;

public class UserKey extends BasePrefix {

    public static UserKey idPrefix = new UserKey("id");

    public static UserKey namePrefix = new UserKey("name");

    private UserKey(String prefix) {
        super(prefix);
    }
}
