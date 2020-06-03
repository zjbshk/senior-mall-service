package cn.infomany.interceptor;

import cn.infomany.common.anno.NoNeedLogin;
import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.constant.Resource;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.util.LoginTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private LoginTokenUtil loginTokenUtil;

    @Value("${access-control-allow-origin-enable}")
    private boolean accessControlAllowOriginEnable;

    @Value("${access-control-allow-origin}")
    private String accessControlAllowOrigin;


    /**
     * 拦截服务器端响应处理ajax请求返回结果
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 跨域设置
        if (accessControlAllowOriginEnable) {
            this.crossDomainConfig(response);
        }

        // 判断是否是方法，不是直接返回true，不拦截
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (!isHandlerMethod) {
            return true;
        }

        // 不需要登录的注解
        if (((HandlerMethod) handler).hasMethodAnnotation(NoNeedLogin.class)) {
            return true;
        }

        // 需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(Resource.TOKEN);
        String xRequestToken = request.getParameter(Resource.TOKEN);
        String xAccessToken = (Objects.nonNull(xHeaderToken) ? xHeaderToken : xRequestToken);
        if (Objects.isNull(xAccessToken)) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGGED_IN);
        }

        // 根据token获取登录用户
        try {
            Long no = loginTokenUtil.getNoByToken(xAccessToken);
            request.setAttribute(Resource.USER_NO, no);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_EXPIRED);
        }

        return true;
    }

    /**
     * 配置跨域
     *
     * @param response
     */
    private void crossDomainConfig(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }

}
