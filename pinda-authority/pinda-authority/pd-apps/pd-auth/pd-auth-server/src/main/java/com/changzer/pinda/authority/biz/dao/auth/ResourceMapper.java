package com.changzer.pinda.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lingqu
 * @date 2022/10/7
 * @apiNote
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
