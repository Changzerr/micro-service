package com.changzer.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changzer.pinda.authority.entity.common.OptLog;
import com.changzer.pinda.log.entity.OptLogDTO;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
public interface OptLogService extends IService<OptLog> {
    void save(OptLogDTO optLogDTO);
}
