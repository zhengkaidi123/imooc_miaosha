package swjtu.zkd.miaosha.redis;

public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public static UserKey tokenPrefix = new UserKey( TOKEN_EXPIRE,"token");

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


}
