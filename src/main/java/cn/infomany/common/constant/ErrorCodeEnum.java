package cn.infomany.common.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * 错误码枚举
 *
 * @author zjb
 */
@Slf4j
public enum ErrorCodeEnum {

    /**
     * 系统功能异常 0 - 1000
     */
    SUCCESS(0, "成功", "Successful"),
    ERROR_PARAM(101, "请求缺少参数[%s]", "Request missing parameter [%s]"),
    DEVELOPMENT(112, "此功能正在开发中", "This feature is under development"),
    NOT_EXISTS(113, "数据不存在", "Data does not exist"),
    REQUEST_METHOD_ERROR(114, "请求方式错误", "Incorrect request method"),
    PARAMETER_RESOLUTION_FAILED(115, "参数解析失败", "Parameter resolution failed"),
    PARAMETER_VERIFICATION_FAILED(116, "参数验证失败", "Parameter verification failed"),
    PARAMETER_BINDING_FAILED(117, "参数绑定失败", "Parameter binding failed"),
    NOT_SUPPORT_THE_CURRENT_REQUEST_METHOD(118, "不支持当前请求方法", "Not support the current request method"),
    ABNORMAL_HEAD_LOSS(119, "头丢失异常", "Abnormal head loss"),

    /*访问频率*/
    ACCESS_FREQUENCY_IS_TOO_FAST_TO_RESPOND(120, "访问频率过快无法响应", "Access frequency is too fast to respond"),
    ACCESS_TOO_FAST_IP_OR_USER_IS_RESTRICTED(121, "访问过快IP或用户被限制", "Access too fast IP or user is restricted"),

    /**
     * 业务异常从这里开始 每次增加100个
     */

    /*====================登录有关错误码 [1100 - 1200) =======================*/
    NOT_LOGGED_IN(1100, "未登录", "Not logged in"),
    INCORRECT_USERNAME_OR_PASSWORD(1101, "账号或密码错误", "Incorrect username or password"),
    ACCOUNT_IS_FREEZE(1102, "账号被冻结", "Account is freeze"),
    TOKEN_EXPIRED(1103, "token过期", "Token expired"),
    THE_DEVICE_WAS_SQUEEZED_OFF_LINE_TOKEN_INVALID(1104, "设备被挤下线，令牌失效", "The device is squeezed offline token invalid"),


    ;

    private int code;

    private String msg;

    private String enMsg;

    ErrorCodeEnum(int code, String msg, String enMsg) {
        this.code = code;
        this.msg = msg;
        this.enMsg = enMsg;
    }

    public int getCode() {
        return code;
    }

    public ErrorCodeEnum setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ErrorCodeEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public ErrorCodeEnum setEnMsg(String enMsg) {
        this.enMsg = enMsg;
        return this;
    }

}
