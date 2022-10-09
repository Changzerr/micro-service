package com.changzer.pinda.zuul.api;

import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import com.changzer.pinda.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源API熔断
 */
@Component
public class ResourceApiFallback implements ResourceApi {
    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        return null;
    }
}
