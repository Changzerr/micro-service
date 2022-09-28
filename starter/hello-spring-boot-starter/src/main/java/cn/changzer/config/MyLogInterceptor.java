package cn.changzer.config;

import cn.changzer.log.MyLog;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author lingqu
 * @date 2022/9/28
 * @apiNote
 */
/**
 * 日志拦截器
 */
public class MyLogInterceptor extends HandlerInterceptorAdapter {
    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();//获得被拦截的方法对象
        MyLog myLog = method.getAnnotation(MyLog.class);//获得方法上的注解

        if(myLog != null){
            //方法上加了MyLog注解，需要进行日志记录
            long startTime = System.currentTimeMillis();
            startTimeThreadLocal.set(startTime);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();//获得被拦截的方法对象
        MyLog myLog = method.getAnnotation(MyLog.class);//获得方法上的注解

        if(myLog != null){
            ///方法上加了MyLog注解，需要进行日志记录
            long endTime = System.currentTimeMillis();
            Long startTime = startTimeThreadLocal.get();
            long optTime = endTime - startTime;

            String requestUri = request.getRequestURI();
            String methodName = method.getDeclaringClass().getName() + "." +
                    method.getName();
            String methodDesc = myLog.desc();

            System.out.println("请求uri：" + requestUri);
            System.out.println("请求方法名：" + methodName);
            System.out.println("方法描述：" + methodDesc);
            System.out.println("方法执行时间：" + optTime + "ms");
        }
    }
}
