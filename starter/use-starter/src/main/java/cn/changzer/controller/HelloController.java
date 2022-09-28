package cn.changzer.controller;

import cn.changzer.log.MyLog;
import cn.changzer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lingqu
 * @date 2022/9/28
 * @apiNote
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    //HelloService在我们自定义的starter中已经完成了自动配置，所以此处可以直接注入
    @Autowired
    private HelloService helloService;

    @GetMapping("/say")
    @MyLog
    public String sayHello(){
        return helloService.sayHello();
    }
}
