package cn.infomany.module.user.dictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 用户登录表中的状态枚举
 *
 * @author zjb
 * @date 2020/6/2
 */
public enum LoginUserStateEnum {

    /**
     * 用户登录表中的状态枚举
     */
    NORMAL(0, "正常"),
    FREEZE(1, "冻结"),
    FORBID(2, "禁止"),
    DELETE(3, "删除"),

    ;

    private Integer state;
    private String desc;

    LoginUserStateEnum(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public boolean is(Integer code) {
        return this.state.equals(code);
    }

    public static LoginUserStateEnum valueOf(Integer state) {
        List<LoginUserStateEnum> loginUserStateEnums = Arrays.asList(values());
        Optional<LoginUserStateEnum> firstLoginUser = loginUserStateEnums
                .stream().filter(loginUserStateEnum -> loginUserStateEnum.is(state)).findFirst();
        return firstLoginUser.orElseThrow(() -> new IllegalArgumentException("LoginUserStateEnum不存在类型[" + state + "]"));
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
