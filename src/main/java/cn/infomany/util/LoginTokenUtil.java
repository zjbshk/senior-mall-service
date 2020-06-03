package cn.infomany.util;

import cn.infomany.module.user.domain.vo.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * token生成与解析工具类
 *
 * @author zjb
 */
@Slf4j
@Component
public class LoginTokenUtil {

    /**
     * 过期时间一天
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;
    /**
     * jwt加密字段
     */
    private static final String CLAIM_ID_KEY = "no";

    @Value("${jwt.key}")
    private String jwtKey;


    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    /**
     * 生成JWT TOKEN
     *
     * @param no 用户逻辑主键no
     * @return token
     */
    public TokenInfo generateToken(Long no) {
        // 将token设置为jwt格式
        String baseToken = UUID.randomUUID().toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeExpire = localDateTimeNow.plusSeconds(expireSeconds);
        Date from = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        Date expire = Date.from(localDateTimeExpire.atZone(ZoneId.systemDefault()).toInstant());

        Claims jwtClaims = Jwts.claims().setSubject(baseToken);
        jwtClaims.put(CLAIM_ID_KEY, no);

        String token = Jwts.builder()
                .setClaims(jwtClaims)
                .setNotBefore(from)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();

        return TokenInfo.builder().token(token).expired(expire.getTime()).build();
    }

    /**
     * 根据登陆token获取登陆信息
     *
     * @param token 上面生成的token
     * @return no 用户逻辑主键no
     */
    public Long getNoByToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
            String noStr = claims.get(CLAIM_ID_KEY).toString();
            return Long.valueOf(noStr);
        } catch (Exception e) {
            log.error("解析token获取no失败", e);
            throw new IllegalArgumentException("解析token获取no失败");
        }
    }

}
