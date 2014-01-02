package com.cplatform.sapi.cache;

import com.cplatform.sapi.data.CacheData;
import com.cplatform.sapi.util.SpringContextTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

/**
 * User: cuikai
 * Date: 13-11-1
 * Time: 下午12:56
 */
@ContextConfiguration(locations = {"/applicationContext.xml", "/applicationContext-test-cache.xml"})
public class CacheTest extends SpringContextTest {

    @Autowired
    private CacheData cacheData;

    @Test
    public void springXmemTest() throws Exception {

        Thread.sleep(2000);
        String key = "key";

        String content = cacheData.getOne(key, "");
        Thread.sleep(1000);
        assertNull(content);

        content = cacheData.getOne(key, "11");
        assertNull(content);

        Thread.sleep(1000);
        content = cacheData.getOne(key, "11");
        assertEquals("key", content);

        cacheData.invalidCache(key);

        Thread.sleep(1000);
        content = cacheData.getOne(key, "");
        assertNull(content);
    }

}
