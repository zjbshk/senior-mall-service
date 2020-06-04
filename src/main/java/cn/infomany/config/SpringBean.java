package cn.infomany.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户加载系统中的Bean
 *
 * @author zjb
 * @date 2020/6/2
 */
@Configuration
public class SpringBean {

    @Value("${spring.snowflake.workerId}")
    private int workerId;

    @Value("${spring.snowflake.dataCenterId}")
    private int dataCenterId;


    @Bean
    public Sequence sequence() {
        return new Sequence(workerId, dataCenterId);
    }

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%d", redisHost, redisPort);
        config.useSingleServer()
                .setTimeout(3000)
                .setAddress(redisAddress);
        return Redisson.create(config);
    }
}
