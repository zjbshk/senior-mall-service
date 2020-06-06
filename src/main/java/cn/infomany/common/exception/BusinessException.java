package cn.infomany.common.exception;

import cn.infomany.common.constant.ErrorCodeEnum;

/**
 * 业务逻辑异常,全局异常拦截后统一返回Result
 *
 * @author zjb
 */
public class BusinessException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public int getCode() {
        return errorCodeEnum.getCode();
    }

    public String getMsg() {
        return errorCodeEnum.getMsg();
    }

    public String getEnMsg() {
        return errorCodeEnum.getEnMsg();
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
