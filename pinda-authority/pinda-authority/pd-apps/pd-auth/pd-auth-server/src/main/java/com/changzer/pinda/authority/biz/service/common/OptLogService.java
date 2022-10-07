package com.changzer.pinda.authority.biz.service.common;
import com.baomidou.mybatisplus.extension.service.IService;
import com.changzer.pinda.authority.entity.common.OptLog;
import com.changzer.pinda.log.entity.OptLogDTO;
/**
 * 业务接口
 * 操作日志
 */
public interface OptLogService extends IService<OptLog> {
    /**
     * 保存日志
     */
    boolean save(OptLogDTO entity);
}
