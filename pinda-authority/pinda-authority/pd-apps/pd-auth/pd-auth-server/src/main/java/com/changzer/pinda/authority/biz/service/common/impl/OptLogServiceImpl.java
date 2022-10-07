package com.changzer.pinda.authority.biz.service.common.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changzer.pinda.authority.biz.dao.common.OptLogMapper;
import com.changzer.pinda.authority.entity.common.OptLog;
import com.changzer.pinda.dozer.DozerUtils;
import com.changzer.pinda.log.entity.OptLogDTO;
import com.changzer.pinda.authority.biz.service.common.OptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 业务实现类
 * 操作日志
 */
@Slf4j
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {
    @Autowired
    DozerUtils dozer;

    @Override
    public boolean save(OptLogDTO entity) {
        return super.save(dozer.map(entity, OptLog.class));
    }
}
