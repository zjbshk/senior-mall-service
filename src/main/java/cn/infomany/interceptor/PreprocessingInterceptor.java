package cn.infomany.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author zjb
 */
@Slf4j
@Component
public class PreprocessingInterceptor extends HandlerInterceptorAdapter {

    @Value("${access-control-allow-origin-enable}")
    private boolean accessControlAllowOriginEnable;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域设置
        if (accessControlAllowOriginEnable) {
            this.crossDomainConfig(request, response);
        }
        return true;
    }

    /**
     * 配置跨域
     *
     * @param request
     * @param response
     */
    private void crossDomainConfig(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }

}
