package swjtu.zkd.miaosha.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            return stringToBean(str, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> T stringToBean(String key, Class<T> clazz) {
        if (key == null || key.length() <= 0 || clazz == null) {
            return null;
        }
        return JSONObject.parseObject(key, clazz);
    }

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String str = beanToString(value);
            if (str == null) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int expireSeconds = prefix.expireSeconds();
            if (expireSeconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expireSeconds, str);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        return JSONObject.toJSONString(value);
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @return
     */
    public boolean exists(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * value增加1
     * @param prefix
     * @param key
     * @return
     */
    public long incr(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }
    }

    /**
     * value减少1
     * @param prefix
     * @param key
     * @return
     */
    public long decr(KeyPrefix prefix, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }
    }
}
