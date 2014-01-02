package com.cplatform.sapi.util;

import com.cplatform.sapi.orm.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * User: chenke
 * Date: 13-10-31
 * Time: 上午10:03
 */
public class ListFormat {

    public static Map format(Page page) {
        if (page.getTotalItems()<1){
            Map map1 = new HashMap();
            map1.put("MSG","No rows");
            map1.put("FLAG","-1");
            return map1;
        }
        Map map = new HashMap<String, Object>();
        map.put("data",page.getResult());
        map.put("totalItems", page.getTotalItems());
        return map;
    }

}
