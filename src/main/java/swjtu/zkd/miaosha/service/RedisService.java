package swjtu.zkd.miaosha.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            return stringToBean(key, clazz);
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

    public <T> boolean set(String key, T value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String str = beanToString(value);
            if (str == null) {
                return false;
            }
            jedis.set(key, str);
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
}
