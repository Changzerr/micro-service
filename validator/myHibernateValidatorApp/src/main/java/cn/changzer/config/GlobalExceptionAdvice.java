package cn.changzer.config;

import cn.hutool.http.HttpException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handlerException(HttpServletRequest request, Exception e) {
        log.error("not known  error, "  + e.getMessage());

        return Result.error(300,e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handlerHttpException(HttpServletRequest request, BindException e) {
        log.error("not found  error, "  + e.getMessage());
        e.printStackTrace();
        return Result.error(4000, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpException.class)
    @ResponseBody
    public Result handlerHttpException(HttpServletRequest request, HttpException e) {
         log.error("not found  error, "  + e.getMessage());

         return Result.error(400, e.getMessage());
    }

   // 预设全局参数绑定
    @ModelAttribute
    public void presetParam(Model model){
        model.addAttribute("globalAttr","this is a global attribute");
    }
}

@Data
class Result{
    private int code;
    private String msg;
    private Object data;

    public static Result error(int code,String message){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(message);
        return r;
    }
}
