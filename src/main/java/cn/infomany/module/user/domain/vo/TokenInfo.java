package cn.infomany.module.user.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 登录VO
 *
 * @author zjb
 * @date 2020/6/2
 */
@Data
@Builder
public class TokenInfo {

    private String token;

    private Long expired;


}
