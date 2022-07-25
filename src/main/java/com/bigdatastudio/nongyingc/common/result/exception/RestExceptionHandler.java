package com.bigdatastudio.nongyingc.common.result.exception;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.bigdatastudio.nongyingc.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author 成大事
 * @since 2022/6/2 11:47
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    public R<String> exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode()),ExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BaseException.class)
    public R<String> baseExceptionHandler(BaseException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return R.error(Integer.parseInt(e.getErrorCode()),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    public R<String> exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.BODY_NOT_MATCH.getResultCode()),ExceptionEnum.BODY_NOT_MATCH.getResultMsg());
    }


    /**
     * 处理校验异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<String> constraintViolationExceptionHandler(ConstraintViolationException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.CONSTRAINT_VIOLATION_EXCEPTION.getResultCode()),ExceptionEnum.CONSTRAINT_VIOLATION_EXCEPTION.getResultMsg());
    }



    /**
     * 处理请求参数异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.REQUEST_PARAMETER_EXCEPTION.getResultCode()),ExceptionEnum.REQUEST_PARAMETER_EXCEPTION.getResultMsg());
    }

    /*Sa-token 的一些异常处理*/
    /**
     * 处理没登录异常
     */
    @ExceptionHandler(value = NotLoginException.class)
    public R<String> notLoginExceptionHandler(NotLoginException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.NOT_LOGIN.getResultCode()), ExceptionEnum.NOT_LOGIN.getResultMsg());
    }

    /**
     * 处理角色异常
     */
    @ExceptionHandler(value = NotRoleException.class)
    public R<String> notRoleExceptionHandler(NotRoleException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.NOT_ROLE.getResultCode()), ExceptionEnum.NOT_ROLE.getResultMsg());
    }

    /**
     * 处理权限异常
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public R<String> notPermissionExceptionHandler(NotPermissionException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.NOT_PERMISSION.getResultCode()), ExceptionEnum.NOT_PERMISSION.getResultMsg());
    }

    /**
     * 账号被封禁
     */
    @ExceptionHandler(value = DisableLoginException.class)
    public R<String> disableLoginExceptionHandler(DisableLoginException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.DIS_LOGIN.getResultCode()), ExceptionEnum.DIS_LOGIN.getResultMsg());
    }






}
