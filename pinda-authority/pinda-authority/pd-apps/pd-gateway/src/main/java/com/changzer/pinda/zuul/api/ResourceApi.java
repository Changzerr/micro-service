package com.changzer.pinda.zuul.api;

import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import com.changzer.pinda.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${pinda.feign.authority-server:pd-auth-server}",
             fallback = ResourceApiFallback.class)
public interface ResourceApi {
    //获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    public R<List> list();

    //查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    public R<List<Resource>> visible(ResourceQueryDTO resource);
}
