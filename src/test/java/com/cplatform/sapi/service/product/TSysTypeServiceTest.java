package com.cplatform.sapi.service.product;

import com.cplatform.sapi.test.SpringContextTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午12:20
 */
@ContextConfiguration(locations = {"/applicationContext*.xml"})
public class TSysTypeServiceTest extends SpringContextTest {

//    private TSysTypeService typeService;

    @Test
    public void getList() {
//        List<TSysType> lists = typeService.getByChannel(2L);
        assertEquals("", "");
    }

//    @Autowired
//    public void setTypeService(TSysTypeService typeService) {
//        this.typeService = typeService;
//    }
}
