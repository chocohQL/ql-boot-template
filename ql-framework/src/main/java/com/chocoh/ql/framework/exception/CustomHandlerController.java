package com.chocoh.ql.framework.exception;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.common.exception.BusinessException;
import com.chocoh.ql.common.model.Response;
import com.chocoh.ql.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author chocoh
 */
@Slf4j
@RestControllerAdvice
public class CustomHandlerController {
    /**
     * 全局异常处理器
     */
    @ExceptionHandler(GlobalException.class)
    public Response handleGlobalException(GlobalException e) {
        return Response.error(e.getMessage());
    }

    /**
     * 处理@RequestBody接口参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationException(MethodArgumentNotValidException e) {
        return Response.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response handlerBusinessException(BusinessException e) {
        return new Response(e.getResultCodeEnum());
    }

    /**
     * 处理@RequestParam接口参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        return Response.error(message);
    }

    /**
     * 上传文件过大
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class, FileSizeLimitExceededException.class})
    public Response handleMaxUploadSizeExceededException() {
        return new Response(ResultCodeEnum.MAX_UPLOAD_SIZE_EXCEEDED);
    }
}
