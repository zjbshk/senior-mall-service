package cn.infomany.shiro;

import cn.hutool.core.io.IoUtil;
import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.constant.Resource;
import cn.infomany.common.domain.Result;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.util.LoginTokenUtil;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * Jwt过滤器
 *
 * @author zjb
 * @date 2020/6/6
 */
public class JwtFilter extends AuthenticatingFilter {

    private static final String CHARSET = "UTF-8";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = (String) request.getAttribute(Resource.TOKEN);
        return new JwtToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接抛出异常
        String xAccessToken = LoginTokenUtil.getRequestToken((HttpServletRequest) request);
        request.setAttribute(Resource.TOKEN, xAccessToken);
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=" + CHARSET;
        httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        httpResponse.setContentType(contentType);
        Result result = Result.builder().msg(e.getMessage()).code(HttpServletResponse.SC_BAD_REQUEST).build();
        String jsonString = JSON.toJSONString(result);
        try (ServletOutputStream outputStream = httpResponse.getOutputStream()) {
            IoUtil.write(outputStream, false, jsonString.getBytes());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

}