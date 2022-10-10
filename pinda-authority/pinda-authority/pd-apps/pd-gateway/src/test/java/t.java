import com.changzer.pinda.ZuulServerApplication;
import com.changzer.pinda.common.constant.CacheKey;
import com.changzer.pinda.common.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lingqu
 * @date 2022/10/9
 * @apiNote
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZuulServerApplication.class)
@Slf4j
public class t {
    @Autowired
    RedisCache redisCache;

    @Test
    public void test(){
        //List<String> cacheList = redisCache.getCacheObject("user_resource:3");
        List<String> a = new ArrayList<>();
        a.add("aaa");
        a.add("aaa");
        redisCache.setCacheList("aa", a);
        log.info("Stirng:========");
        List<String> aa = redisCache.getCacheList("aa");
        log.info("Stirng:{}",aa);
    }
}
