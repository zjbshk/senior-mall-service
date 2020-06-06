package cn.infomany.config;

import cn.infomany.common.resolver.UserInfoArgumentResolver;
import cn.infomany.common.resolver.UserNoArgumentResolver;
import cn.infomany.common.resolver.UserSignatureArgumentResolver;
import cn.infomany.interceptor.AuthenticationInterceptor;
import cn.infomany.interceptor.FrequencyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 拦截器配置类
 *
 * @author zjb
 */
@Configuration
public class InterceptorAndArgResolverConfig extends WebMvcConfigurationSupport {


    @Autowired
    private FrequencyInterceptor frequencyInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * interceptor配置
     * 将拦截器加入到spring boot进行管理
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor);
        registry.addInterceptor(frequencyInterceptor);
        //这里可以用registry.addInterceptor添加多个拦截器实例，后面加上匹配模式
        super.addInterceptors(registry);
    }


    /**
     * 将参数解析器加入到springboot中
     *
     * @param argumentResolvers
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new UserNoArgumentResolver());
        argumentResolvers.add(new UserInfoArgumentResolver());
        argumentResolvers.add(new UserSignatureArgumentResolver());
    }
}