package com.changzer.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.changzer.pinda.authority.dto.auth.ResourceQueryDTO;
import com.changzer.pinda.authority.entity.auth.Resource;
import com.changzer.pinda.base.R;
import com.changzer.pinda.common.constant.CacheKey;
import com.changzer.pinda.common.redis.RedisCache;
import com.changzer.pinda.context.BaseContextConstants;
import com.changzer.pinda.exception.code.ExceptionCode;
import com.changzer.pinda.zuul.api.ResourceApi;
import com.changzer.pinda.zuul.filter.BaseFilter;
//import com.changzer.pinda.zuul.utils.RedisCache;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限验证过滤器
 */
@Component
@Slf4j
public class AccessFilter extends BaseFilter {
    //@Autowired
    //private CacheChannel cacheChannel;
    @Autowired
    private RedisCache redisCache;


    @Autowired
    private ResourceApi resourceApi;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 验证当前用户是否拥有某个URI的访问权限
     */
    @Override
    public Object run() {
        // 不进行拦截的地址
        if (isIgnoreToken()) {
            return null;
        }

        RequestContext requestContext = RequestContext.getCurrentContext();
        String requestURI = requestContext.getRequest().getRequestURI();
        requestURI = StrUtil.subSuf(requestURI, requestURI.indexOf("/", 2));
        String method = requestContext.getRequest().getMethod();
        String permission = method + requestURI;

        //从缓存中获取所有需要进行鉴权的资源
        List<String> resourceNeed2Auth = redisCache.getCacheList(CacheKey.RESOURCE + ":" + CacheKey.RESOURCE_NEED_TO_CHECK);
        if(resourceNeed2Auth == null || resourceNeed2Auth.size()==0){
            resourceNeed2Auth = resourceApi.list().getData();
            if(resourceNeed2Auth != null){
                redisCache.setCacheList(CacheKey.RESOURCE + ":" + CacheKey.RESOURCE_NEED_TO_CHECK,resourceNeed2Auth);
                redisCache.expire(CacheKey.RESOURCE + ":" + CacheKey.RESOURCE_NEED_TO_CHECK,2, TimeUnit.HOURS);
            }
        }
        if(resourceNeed2Auth != null){
            long count = resourceNeed2Auth.stream().filter((String r) -> {
                return permission.startsWith(r);
            }).count();
            if(count == 0){
                //未知请求
                errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),
                          ExceptionCode.UNAUTHORIZED.getCode(), 200);
                return null;
            }
        }

        String userId = requestContext.getZuulRequestHeaders().
            				get(BaseContextConstants.JWT_KEY_USER_ID);
        List<String> cacheObject = redisCache.getCacheList(CacheKey.USER_RESOURCE + ":" + userId);
        List<String> userResource = cacheObject;
        // 如果从缓存获取不到当前用户的资源权限，需要查询数据库获取，然后再放入缓存
        if(userResource == null || userResource.size() == 0){
            ResourceQueryDTO resourceQueryDTO = new ResourceQueryDTO();
            resourceQueryDTO.setUserId(new Long(userId));
            //通过Feign调用服务，查询当前用户拥有的权限
            R<List<Resource>> result = resourceApi.visible(resourceQueryDTO);
            if(result.getData() != null){
                List<Resource> userResourceList = result.getData();
                userResource = userResourceList.stream().map((Resource r) -> {
                    return r.getMethod() + r.getUrl();
                }).collect(Collectors.toList());
                redisCache.setCacheList(CacheKey.USER_RESOURCE + ":" + userId,userResource);
            }
        }

        long count = userResource.stream().filter((String r) -> {
            return permission.startsWith(r);
        }).count();

        if(count > 0){
            //有访问权限
            return null;
        }else{
            log.warn("用户{}没有访问{}资源的权限",userId,method + requestURI);
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),
                          ExceptionCode.UNAUTHORIZED.getCode(), 200);
        }
        return null;
    }
}
