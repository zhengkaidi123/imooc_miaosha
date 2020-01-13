package swjtu.zkd.miaosha.redis;

public interface KeyPrefix {

    int getExpireSeconds();

    String getPrefix();
}
