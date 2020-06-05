package cn.infomany.handler;

import cn.infomany.common.constant.ErrorCodeEnum;
import cn.infomany.common.domain.Result;
import cn.infomany.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * [ 全局异常拦截 ]
 *
 * @author zjb
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数缺失，无法获取具体的信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result exceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求缺少参数:{}", e.getParameterName());

        String msg = String.format(ErrorCodeEnum.ERROR_PARAM.getMsg(), e.getParameterName());
        return Result.wrap(ErrorCodeEnum.ERROR_PARAM.setMsg(msg));
    }

    /**
     * 参数解析失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result exceptionHandler(HttpMessageNotReadableException e) {
        String msg = e.getCause() == null ? e.getMessage() : e.getCause().getMessage();
        log.error("参数解析失败:{}", msg);
        return Result.wrap(ErrorCodeEnum.PARAMETER_RESOLUTION_FAILED.setMsg(msg));
    }

    /**
     * 参数验证失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        assert error != null;
        String msg = error.getDefaultMessage();
        log.error("参数验证失败:{}", msg);

        return Result.wrap(ErrorCodeEnum.PARAMETER_VERIFICATION_FAILED.setMsg(msg));
    }

    /**
     * 参数绑定失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result exceptionHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        assert error != null;
        String field = error.getField();
        String code = error.getDefaultMessage();
        String msg = String.format("%s:%s", field, code);
        log.error("参数绑定失败：{}", msg);

        return Result.wrap(ErrorCodeEnum.PARAMETER_BINDING_FAILED.setMsg(msg));
    }

    /**
     * 参数验证失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleServiceException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String msg = violation.getMessage();
        log.error("参数验证失败：{}", msg);

        return Result.wrap(ErrorCodeEnum.PARAMETER_VERIFICATION_FAILED.setMsg(msg));
    }

    /**
     * 参数验证失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Result handleValidationException(ValidationException e) {
        log.error("参数验证失败");

        return Result.wrap(ErrorCodeEnum.PARAMETER_VERIFICATION_FAILED.setMsg(e.getMessage()));
    }

    /**
     * 不支持当前请求方法
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = String.format("该接口不支持[%s]方法,支持%s方法", e.getMethod(), e.getSupportedHttpMethods());
        log.error(msg);

        return Result.wrap(ErrorCodeEnum.NOT_SUPPORT_THE_CURRENT_REQUEST_METHOD.setMsg(msg));
    }

    /**
     * 不支持当前媒体类型
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String msg = String.format("不支持当前媒体类型[%s],支持%s", e.getContentType(), e.getSupportedMediaTypes());
        log.error(msg);

        return Result.wrap(ErrorCodeEnum.NOT_SUPPORT_THE_CURRENT_REQUEST_METHOD.setMsg(msg));
    }

    /**
     * 业务逻辑异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public Result handleServiceException(BusinessException e) {
        log.error("业务逻辑异常:{}", e.getErrorCodeEnum());

        return Result.wrap(e.getErrorCodeEnum());
    }

    /**
     * 头丢失异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Result missingRequestHeaderException(MissingRequestHeaderException e) {
        String msg = String.format("请求头部缺少字段[%s]", e.getHeaderName());
        log.error(msg);

        return Result.wrap(ErrorCodeEnum.ABNORMAL_HEAD_LOSS.setMsg(msg));
    }


    /**
     * 通用异常,最后一道防御
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("通用异常:{}", e.getMessage());
        log.error("输入错误：", e);

        return Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg(e.getMessage()).build();
    }
}
