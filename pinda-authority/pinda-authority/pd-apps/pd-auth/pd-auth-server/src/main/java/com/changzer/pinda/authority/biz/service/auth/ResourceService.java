package com.changzer.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;

import java.util.List;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
public interface ResourceService extends IService<Resource> {
    List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
