package cn.infomany.util;

import cn.infomany.common.constant.Resource;
import cn.infomany.module.user.domain.vo.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * token生成与解析工具类
 *
 * @author zjb
 */
@Slf4j
@Component
public class LoginTokenUtil {

    /**
     * jwt加密字段
     */
    public static final String NO = "no";
    public static final String SIGNATURE = "signature";
    /**
     * 过期时间一天
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;
    @Value("${jwt.key}")
    private String jwtKey;

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        // 优先从请求头部获取，当头部没有的时候再从参数中获取
        return Optional.ofNullable(httpRequest.getHeader(Resource.TOKEN))
                .orElse(httpRequest.getParameter(Resource.TOKEN));
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    /**
     * 生成JWT TOKEN
     *
     * @param no 用户逻辑主键no
     * @return token
     */
    public TokenInfo generateToken(Long no, String signature) {
        // 将token设置为jwt格式
        String baseToken = UUID.randomUUID().toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeExpire = localDateTimeNow.plusSeconds(expireSeconds);
        Date from = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        Date expire = Date.from(localDateTimeExpire.atZone(ZoneId.systemDefault()).toInstant());

        Claims jwtClaims = Jwts.claims().setSubject(baseToken);
        jwtClaims.put(NO, no);
        jwtClaims.put(SIGNATURE, Optional.of(signature).orElse(Resource.EMPTY_STRING));


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
    public String getSignatureByToken(String token) {
        Map<String, Object> mapByToken = getMapByToken(token);
        return mapByToken.get(SIGNATURE).toString();
    }

    /**
     * 根据登陆token获取登陆信息
     *
     * @param token 上面生成的token
     * @return no 用户逻辑主键no
     */
    public Long getNoByToken(String token) {
        Map<String, Object> mapByToken = getMapByToken(token);
        String noStr = mapByToken.get(NO).toString();
        return Long.valueOf(noStr);
    }

    /**
     * 获取no和signature的map
     *
     * @param token
     * @return
     */
    public Map<String, Object> getMapByToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
            Map<String, Object> map = new HashMap<>(2);
            map.putAll(claims);
            return map;
        } catch (Exception e) {
            log.error("解析token获取no失败", e);
            throw e;
        }
    }
}
