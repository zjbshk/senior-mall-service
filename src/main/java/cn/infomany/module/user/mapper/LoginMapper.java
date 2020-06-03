package cn.infomany.module.user.mapper;

import cn.infomany.module.user.domain.entity.LoginEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 登录表-login
 * </p>
 *
 * @author zjb
 * @since 2020-06-02 10:44:37
 */
@Mapper
public interface LoginMapper extends BaseMapper<LoginEntity> {

    /**
     * 通过用户账号（手机号或用户）获取登录账号信息
     *
     * @param account 用户账号
     * @return
     */
    LoginEntity selectLoginByAccount(String account);


}