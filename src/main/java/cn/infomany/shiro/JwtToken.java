package cn.infomany.shiro;


import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Jwt Token 令牌
 *
 * @author zjb
 * @date 2020/6/6
 */
@Data
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }


}
