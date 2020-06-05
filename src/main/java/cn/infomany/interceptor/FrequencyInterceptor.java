package cn.infomany.interceptor;

import cn.infomany.common.anno.FrequencyLimit;
import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.constant.Resource;
import cn.infomany.common.constant.UniquelyIdentifiesEnum;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.third.redis.RedisService;
import cn.infomany.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 登录拦截器
 *
 * @author zjb
 */
@Slf4j
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


        // 获取限制的唯一签名
        UniquelyIdentifiesEnum uniquely = frequencyLimit.uniquely();
        Long userNo = (Long) request.getAttribute(Resource.USER_NO);
        String sign;
        switch (uniquely) {
            case IP_USER:
                String ip = IpUtil.getIp(request);
                sign = String.format("%s:%d", ip, userNo);
                break;
            case IP:
                sign = IpUtil.getIp(request);
                break;
            case USER:
                sign = userNo.toString();
                break;
            case TOKEN:
                sign = request.getHeader(Resource.TOKEN);
                break;
            default:
                sign = Resource.EMPTY_STRING;
                break;
        }

        // 组合获取key
        String key = String.format("%s.%s", frequencyLimit.value(), sign);
        Integer count = redisService.getObject(key, Integer.class);
        redisService.incr(key, 1);
        if (Objects.isNull(count)) {
            redisService.expire(key, frequencyLimit.epoch(), frequencyLimit.timeUnit());
            return true;
        }

        if (frequencyLimit.maxTimes() != -1 && count >= frequencyLimit.maxTimes()) {
            redisService.expire(key, frequencyLimit.limitedTime(), frequencyLimit.limitedTimeUnit());
            log.error("用户[{}],访问次数{}超过，访问受限key:({})", userNo, count + 1, key);
            throw new BusinessException(ErrorCodeEnum.ACCESS_TOO_FAST_IP_OR_USER_IS_RESTRICTED);
        }

        if (count >= frequencyLimit.times()) {
            log.error("用户[{}],访问次数{}超过，访问受限key:({})", userNo, count + 1, key);
            throw new BusinessException(ErrorCodeEnum.ACCESS_FREQUENCY_IS_TOO_FAST_TO_RESPOND);
        }

        return true;
    }


}
