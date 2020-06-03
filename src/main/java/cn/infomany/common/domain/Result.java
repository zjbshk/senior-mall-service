package cn.infomany.common.domain;


import cn.infomany.common.constant.ErrorCodeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 返回类
 *
 * @param
 * @author zhuoda
 */
@Data
@Builder
public class Result {


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
