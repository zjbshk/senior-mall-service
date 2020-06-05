package cn.infomany.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 专门为token设置到redis进行处理
 *
 * @author zjb
 */
@Component
@Slf4j
public class TokenRedisUtil {

    private final static String TOKEN_KEY = "user.no.%s%d";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LoginTokenUtil loginTokenUtil;

    /**
     * 保存对应的token
     *
     * @param no        用户编号
     * @param signature 客户端签名
     */
    public void save(Long no, String signature, String token) {
        String key = String.format(TOKEN_KEY, signature, no);
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, loginTokenUtil.getExpireSeconds(), TimeUnit.SECONDS);
    }

    /**
     * 删除redis中对应的token
     *
     * @param no        用户编号
     * @param signature 客户端签名
     */
    public Boolean del(Long no, String signature) {
        String key = String.format(TOKEN_KEY, signature, no);
        return redisTemplate.delete(key);
    }

    /**
     * 判断redis是否存在token
     *
     * @param no        用户编号
     * @param signature 客户端签名
     */
    public Boolean has(Long no, String signature) {
        String key = String.format(TOKEN_KEY, signature, no);
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取redis存在token,没有返回null
     *
     * @param no        用户编号
     * @param signature 客户端签名
     */
    public String get(Long no, String signature) {
        String key = String.format(TOKEN_KEY, signature, no);
        return (String) redisTemplate.opsForValue().get(key);
    }


}
