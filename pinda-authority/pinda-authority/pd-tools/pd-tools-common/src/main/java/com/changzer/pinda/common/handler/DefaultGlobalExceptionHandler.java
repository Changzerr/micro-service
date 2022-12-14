package com.changzer.pinda.common.handler;

import cn.hutool.core.util.StrUtil;
import com.changzer.pinda.base.R;
import com.changzer.pinda.exception.BizException;
import com.changzer.pinda.exception.code.ExceptionCode;
import com.changzer.pinda.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 *
 */
//@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
@Slf4j
public abstract class DefaultGlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public R<String> bizException(BizException ex, HttpServletRequest request) {
        log.warn("BizException:", ex);
        return R.result(ex.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("HttpMessageNotReadableException:", ex);
        String message = ex.getMessage();
        if (StrUtil.containsAny(message, "Could not read document:")) {
            String msg = String.format("?????????????????????json??????????????????%s", StrUtil.subBetween(message, "Could not read document:", " at "));
            return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg).setPath(request.getRequestURI());
        }
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public R bindException(BindException ex, HttpServletRequest request) {
        log.warn("BindException:", ex);
        try {
            String msgs = ex.getBindingResult().getFieldError().getDefaultMessage();
            if (StrUtil.isNotEmpty(msgs)) {
                return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msgs).setPath(request.getRequestURI());
            }
        } catch (Exception ee) {
        }
        StringBuilder msg = new StringBuilder();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        fieldErrors.forEach((oe) ->
                msg.append("??????:[").append(oe.getObjectName())
                        .append(".").append(oe.getField())
                        .append("]????????????:[").append(oe.getRejectedValue()).append("]?????????????????????????????????.")
        );
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("MethodArgumentTypeMismatchException:", ex);
        MethodArgumentTypeMismatchException eee = (MethodArgumentTypeMismatchException) ex;
        StringBuilder msg = new StringBuilder("?????????[").append(eee.getName())
                .append("]???????????????[").append(eee.getValue())
                .append("]???????????????????????????[").append(eee.getRequiredType().getName()).append("]?????????");
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalStateException.class)
    public R illegalStateException(IllegalStateException ex, HttpServletRequest request) {
        log.warn("IllegalStateException:", ex);
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, ExceptionCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("MissingServletRequestParameterException:", ex);
        StringBuilder msg = new StringBuilder();
        msg.append("???????????????[").append(ex.getParameterType()).append("]???????????????[").append(ex.getParameterName()).append("]");
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public R nullPointerException(NullPointerException ex, HttpServletRequest request) {
        log.warn("NullPointerException:", ex);
        return R.result(ExceptionCode.NULL_POINT_EX.getCode(), StrPool.EMPTY, ExceptionCode.NULL_POINT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException:", ex);
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, ExceptionCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.warn("HttpMediaTypeNotSupportedException:", ex);
        MediaType contentType = ex.getContentType();
        if (contentType != null) {
            StringBuilder msg = new StringBuilder();
            msg.append("????????????(Content-Type)[").append(contentType.toString()).append("] ???????????????????????????????????????");
            return R.result(ExceptionCode.MEDIA_TYPE_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
        }
        return R.result(ExceptionCode.MEDIA_TYPE_EX.getCode(), StrPool.EMPTY, "?????????Content-Type??????").setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public R missingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request) {
        log.warn("MissingServletRequestPartException:", ex);
        return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(ServletException.class)
    public R servletException(ServletException ex, HttpServletRequest request) {
        log.warn("ServletException:", ex);
        String msg = "UT010016: Not a multi part request";
        if (msg.equalsIgnoreCase(ex.getMessage())) {
            return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg());
        }
        return R.result(ExceptionCode.SYSTEM_BUSY.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MultipartException.class)
    public R multipartException(MultipartException ex, HttpServletRequest request) {
        log.warn("MultipartException:", ex);
        return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    /**
     * jsr ????????????????????????
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("ConstraintViolationException:", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return R.result(ExceptionCode.BASE_VALID_PARAM.getCode(), StrPool.EMPTY, message).setPath(request.getRequestURI());
    }

    /**
     * spring ?????????????????????????????? ???conttoller????????????result?????????????????????
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException:", ex);
        return R.result(ExceptionCode.BASE_VALID_PARAM.getCode(), StrPool.EMPTY, ex.getBindingResult().getFieldError().getDefaultMessage()).setPath(request.getRequestURI());
    }

    /**
     * ????????????
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> otherExceptionHandler(Exception ex, HttpServletRequest request) {
        log.warn("Exception:", ex);
        if (ex.getCause() instanceof BizException) {
            return this.bizException((BizException) ex.getCause(), request);
        }
        return R.result(ExceptionCode.SYSTEM_BUSY.getCode(), StrPool.EMPTY, ExceptionCode.SYSTEM_BUSY.getMsg()).setPath(request.getRequestURI());
    }


    /**
     * ???????????????:405
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("HttpRequestMethodNotSupportedException:", ex);
        return R.result(ExceptionCode.METHOD_NOT_ALLOWED.getCode(), StrPool.EMPTY, ExceptionCode.METHOD_NOT_ALLOWED.getMsg()).setPath(request.getRequestURI());
    }


    @ExceptionHandler(PersistenceException.class)
    public R<String> persistenceException(PersistenceException ex, HttpServletRequest request) {
        log.warn("PersistenceException:", ex);
        if (ex.getCause() instanceof BizException) {
            BizException cause = (BizException) ex.getCause();
            return R.result(cause.getCode(), StrPool.EMPTY, cause.getMessage());
        }
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MyBatisSystemException.class)
    public R<String> myBatisSystemException(MyBatisSystemException ex, HttpServletRequest request) {
        log.warn("PersistenceException:", ex);
        if (ex.getCause() instanceof PersistenceException) {
            return this.persistenceException((PersistenceException) ex.getCause(), request);
        }
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(SQLException.class)
    public R sqlException(SQLException ex, HttpServletRequest request) {
        log.warn("SQLException:", ex);
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("DataIntegrityViolationException:", ex);
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

}
