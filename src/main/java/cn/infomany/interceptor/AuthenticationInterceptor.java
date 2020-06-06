package cn.infomany.interceptor;

import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.constant.Resource;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.util.LoginTokenUtil;
import cn.infomany.util.TokenRedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录拦截器
 *
 * @author zjb
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private LoginTokenUtil loginTokenUtil;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断是否是方法，不是直接返回true，不拦截
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (!isHandlerMethod) {
            return true;
        }

        // 不需要登录的注解
        if (((HandlerMethod) handler).hasMethodAnnotation(RequiresGuest.class)) {
            return true;
        }

        // 需要做token校验, 消息头的token优先于请求query参数的token
        String xAccessToken = (String) request.getAttribute(Resource.TOKEN);

        // 根据token获取登录用户
        Map<String, Object> map;
        try {
            map = loginTokenUtil.getMapByToken(xAccessToken);
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_EXPIRED);
        }

        Long no = (Long) map.get(LoginTokenUtil.NO);
        String signature = (String) map.get(LoginTokenUtil.SIGNATURE);


        String token = tokenRedisUtil.get(no, signature);
        // 判断是不是退出登录了
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_INVALID);
        }

        // 判断token是不是一样的，不是代表被挤下线
        if (!token.equals(xAccessToken)) {
            throw new BusinessException(ErrorCodeEnum.THE_DEVICE_WAS_SQUEEZED_OFF_LINE_TOKEN_INVALID);
        }

        // 设置request中的参数
        request.setAttribute(Resource.USER_NO, no);
        request.setAttribute(Resource.SIGNATURE, signature);

        return true;
    }
}
