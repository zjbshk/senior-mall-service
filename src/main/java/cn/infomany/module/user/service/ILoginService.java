package cn.infomany.module.user.service;

import cn.infomany.common.domain.Result;
import cn.infomany.module.user.domain.dto.LoginDTO;
import cn.infomany.module.user.domain.entity.LoginEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录表-login
 * </p>
 *
 * @author zjb
 * @since 2020-06-02 10:44:37
 */
public interface ILoginService extends IService<LoginEntity> {

    /**
     * 登录方法
     *
     * @param loginDTO 登录参数
     * @return
     */
    Result login(LoginDTO loginDTO);


    /**
     * 登出方法，将token在redis中设置为过期
     *
     * @param no
     * @param signature
     * @return
     */
    Result logout(Long no, String signature);
}


