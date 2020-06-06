package cn.infomany.shiro;

import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.exception.BusinessException;
import cn.infomany.util.LoginTokenUtil;
import cn.infomany.util.TokenRedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Jwt 认证器
 *
 * @author zjb
 * @date 2020/6/6
 */
@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private LoginTokenUtil loginTokenUtil;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long no = (Long) principals.getPrimaryPrincipal();


        //用户权限列表
//        Set<String> permsSet = shiroService.getUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(permsSet);
//        info.setRoles(new String[]{"zjb","admin"});
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //getCredentials getPrincipal getToken 都是返回jwt生成的token串
        String xAccessToken = (String) authenticationToken.getCredentials();
        if (Objects.isNull(xAccessToken)) {
            throw new AccountException(ErrorCodeEnum.NOT_LOGGED_IN.getMsg());
        }
        // 根据token获取登录用户
        Long no;
        try {
            no = loginTokenUtil.getNoByToken(xAccessToken);
        } catch (ExpiredJwtException e) {
            throw new AccountException(ErrorCodeEnum.TOKEN_EXPIRED.getMsg());
        } catch (Exception e) {
            throw new AccountException(ErrorCodeEnum.TOKEN_ERROR.getMsg());
        }

        return new SimpleAuthenticationInfo(no, xAccessToken, getName());
    }
}
