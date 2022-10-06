package cn.changzer.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.itheima.pinda.common.handler.DefaultGlobalExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lingqu
 * @date 2022/10/6
 * @apiNote
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends DefaultGlobalExceptionHandler{
    //@ExceptionHandler(Exception.class)
    //public String handleException(Exception e){
    //    System.out.println("统一处理异常信息:" + e.getMessage());
    //    return "系统错误";
    //}
}
