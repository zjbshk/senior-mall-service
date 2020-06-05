package cn.infomany.interceptor;

import cn.infomany.common.anno.FrequencyLimit;
import cn.infomany.common.anno.NoNeedLogin;
import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.constant.Resource;
import cn.infomany.common.constant.UniquelyIdentifiesEnum;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.third.redis.RedisService;
import cn.infomany.util.IpUtil;
import cn.infomany.util.LoginTokenUtil;
import cn.infomany.util.TokenRedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * 登录拦截器
 *
 * @author zjb
 */
@Component
public class FrequencyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否是方法，不是直接返回true，不拦截
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (!isHandlerMethod) {
            return true;
        }

        // 没有标FrequencyLimit，代表不进行访问限制
        FrequencyLimit frequencyLimit = ((HandlerMethod) handler).getMethodAnnotation(FrequencyLimit.class);
        if (Objects.isNull(frequencyLimit)) {
            return true;
        }


        UniquelyIdentifiesEnum uniquely = frequencyLimit.uniquely();
        if (UniquelyIdentifiesEnum.IP.equals(uniquely)) {
            String ip = IpUtil.getIp(request);
            String key = String.format("%s.%s", frequencyLimit.value(), ip);
            Integer count = redisService.getObject(key, Integer.class);
            redisService.incr(key, 1);
            if (Objects.isNull(count)) {
                redisService.expire(key, frequencyLimit.epoch(), frequencyLimit.timeUnit());
            } else {
                if (count > frequencyLimit.times()) {
                    throw new BusinessException(ErrorCodeEnum.ACCESS_FREQUENCY_IS_TOO_FAST_TO_RESPOND);
                }

            }
        }
        return false;

    }


}
