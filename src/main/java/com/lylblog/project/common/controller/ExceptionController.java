package com.lylblog.project.common.controller;

import com.lylblog.common.util.http.CookieUtil;
import com.lylblog.project.common.bean.ResultObj;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.UnknownSessionException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 捕捉shiro的权限不足异常
     * */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResultObj handleAuthorizationException(HttpServletRequest request) {
        return ResultObj.fail(1,"无该权限的操作权，请联系博主！");
    }

    @ExceptionHandler(UnknownSessionException.class)
    public void handleUnknownSessionException(HttpServletResponse response) {
        CookieUtil.set(response, "sid", null, 0);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResultObj handle1(ConstraintViolationException ex){
        StringBuilder msg = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            String message = constraintViolation.getMessage();
            msg.append("[").append(message).append("]");
        }
        logger.error(msg.toString(),ex);
        // 注意：Response类必须有get和set方法，不然会报错
        return ResultObj.fail(msg);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultObj handle2(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        if(bindingResult!=null){
            if(bindingResult.hasErrors()){
                FieldError fieldError = bindingResult.getFieldError();
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                logger.error(ex.getMessage(),ex);
                return ResultObj.fail(defaultMessage);
            }else {
                logger.error(ex.getMessage(),ex);
                return ResultObj.fail(ex.getMessage());
            }
        }else {
            logger.error(ex.getMessage(),ex);
            return ResultObj.fail(ex.getMessage());
        }
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultObj handle1(Exception ex){
        logger.error(ex.getMessage(),ex);
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResultObj.fail("系统异常！");
    }
}
