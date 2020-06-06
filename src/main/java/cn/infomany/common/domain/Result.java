package cn.infomany.common.domain;


import cn.infomany.common.constant.ErrorCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回类
 *
 * @param
 * @author zjb
 */
@Data
@Builder
public class Result implements Serializable {


    private Integer code;

    private String msg;

    private Object data;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        Result wrap = wrap(ErrorCodeEnum.SUCCESS);
        wrap.setData(data);
        return wrap;
    }

    public static Result wrap(ErrorCodeEnum codeEnum) {
        return Result.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
    }

    public static Result wrapEn(ErrorCodeEnum codeEnum) {
        return Result.builder().code(codeEnum.getCode()).msg(codeEnum.getEnMsg()).build();
    }


}
