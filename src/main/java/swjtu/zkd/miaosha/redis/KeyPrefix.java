package swjtu.zkd.miaosha.redis;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
