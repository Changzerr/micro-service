package com.changzer.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changzer.pinda.authority.biz.dao.auth.OptLogMapper;
import com.changzer.pinda.authority.biz.service.auth.OptLogService;
import com.changzer.pinda.authority.entity.common.OptLog;
import com.changzer.pinda.dozer.DozerUtils;
import com.changzer.pinda.log.entity.OptLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
@Service
@Slf4j
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService{
    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public void save(OptLogDTO optLogDTO) {
        OptLog optLog = dozerUtils.map(optLogDTO, OptLog.class);
        baseMapper.insert(optLog);
    }
}
