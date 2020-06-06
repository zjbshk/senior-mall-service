package cn.infomany.module.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录DTO
 *
 * @author zjb
 * @date 2020/6/2
 */
@Data
public class LoginDTO {

    @NotNull(message = "账号[account]不能为空")
    @NotBlank(message = "账号不能为空")
    private String account;


    @NotNull(message = "密码[password]不能为空")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 客户端签名，用来区分多个端登录
     */
    @NotNull(message = "签名[signature]不能为空")
    private String signature;
}
