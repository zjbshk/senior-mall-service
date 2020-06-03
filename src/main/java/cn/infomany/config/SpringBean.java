package cn.infomany.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * 用户加载系统中的Bean
 *
 * @author zjb
 * @date 2020/6/2
 */
public class SpringBean {

    @Value("${spring.snowflake.workerId}")
    private int workerId;

    @Value("${spring.snowflake.dataCenterId}")
    private int dataCenterId;

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(workerId, dataCenterId);
    }
}
