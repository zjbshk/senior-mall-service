package cn.infomany;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author zjb
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("======================================================");
        log.info("======================================================");
        log.info("\t\t\t\t服务启动啦.....");
        log.info("======================================================");
        log.info("======================================================");
    }
}
