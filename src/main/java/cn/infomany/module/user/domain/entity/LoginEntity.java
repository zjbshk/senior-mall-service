package cn.infomany.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 登录表-login
 * </p>
 *
 * @author zjb
 * @since 2020-06-02 10:44:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("senior-login")
public class LoginEntity {

    /**
     * 流水主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 逻辑主键编号，其他的关联表的逻辑外键
     */
    private Long no;

    /**
     * 用户手机号，可以用来登录
     */
    private String phone;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户密码加密盐
     */
    private String salt;

    /**
     * 用户状态(正常:0)，默认为0
     */
    private Integer state;

    /**
     * 数据最近更新时间
     */
    private Date updateTime;

    /**
     * 数据创建时间
     */
    private Date createTime;

}

