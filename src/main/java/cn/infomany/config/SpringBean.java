package cn.infomany.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
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
    public DefaultIdentifierGenerator defaultIdentifierGenerator() {
        return new DefaultIdentifierGenerator(workerId, dataCenterId);
    }


}
