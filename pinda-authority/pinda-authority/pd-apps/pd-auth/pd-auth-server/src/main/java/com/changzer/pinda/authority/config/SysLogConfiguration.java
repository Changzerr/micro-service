package com.changzer.pinda.authority.config;

import com.changzer.pinda.authority.biz.service.auth.OptLogService;
import com.changzer.pinda.authority.entity.common.OptLog;
import com.changzer.pinda.log.entity.OptLogDTO;
import com.changzer.pinda.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.function.Consumer;

/**
 * 系统操作日志配置类
 */
@Configuration
@EnableAsync
public class SysLogConfiguration {
    //创建日志记录的监听器
    @Bean
    public SysLogListener sysLogListener(OptLogService service){
        Consumer<OptLogDTO> consumer = (optLog)->service.save(optLog);
        return new SysLogListener(consumer);
    }
}
