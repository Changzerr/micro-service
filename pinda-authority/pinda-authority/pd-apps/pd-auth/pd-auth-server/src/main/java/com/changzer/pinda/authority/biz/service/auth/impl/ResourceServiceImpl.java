package com.changzer.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changzer.pinda.authority.biz.dao.auth.ResourceMapper;
import com.changzer.pinda.authority.biz.service.auth.ResourceService;
import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
@Service
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Override
    public List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO) {
        return baseMapper.findVisibleResource(resourceQueryDTO);
    }
}
