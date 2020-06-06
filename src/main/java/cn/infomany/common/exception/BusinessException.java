package cn.infomany.common.exception;

import cn.infomany.common.constant.ErrorCodeEnum;
import org.apache.shiro.authc.AuthenticationException;

/**
 * [ 业务逻辑异常,全局异常拦截后统一返回ResponseCodeConst.SYSTEM_ERROR ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
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
