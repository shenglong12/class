package com.kuafu.common.exception;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author kuafui
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, message);
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse<?> handleBindException(BindException e) {
        ObjectError error = e.getAllErrors().get(0);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        if (error instanceof FieldError) {
            String filed = ((FieldError) error).getField();
            message = filed + ":" + message;
        }
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, message);
    }

    @ExceptionHandler(com.wechat.pay.java.core.exception.ServiceException.class)
    public BaseResponse<?> handleServiceException(com.wechat.pay.java.core.exception.ServiceException e) {
        final String responseBody = e.getResponseBody();

        String message = "支付操作失败,请检查后台配置是否正确";
//      判断是否可以转化为json
        try {
            final JSONObject objects = JSONUtil.parseObj(responseBody);
            message = objects.getStr("message");
        } catch (Exception e1) {
            log.error("异常信息转化失败", e1);

        }
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, message);
    }
}
