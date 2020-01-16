package swjtu.zkd.miaosha.redis;

public class UserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private static final int ID_EXPIRE = 0;

    public static UserKey tokenPrefix = new UserKey( TOKEN_EXPIRE,"token");

    public static KeyPrefix userPrefix = new UserKey(ID_EXPIRE, "user");

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


}
