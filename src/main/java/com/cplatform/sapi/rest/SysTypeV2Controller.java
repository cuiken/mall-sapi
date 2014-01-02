package com.cplatform.sapi.rest;

import com.cplatform.sapi.service.ItemSaleV2Service;
import com.cplatform.sapi.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 下午6:37
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Controller
@RequestMapping(value = "/api/v2/sysType")
public class SysTypeV2Controller {

    @Autowired
    ItemSaleV2Service itemSaleV2Service;

    @RequestMapping(value = "path/{typeid}", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public Object sysTypePath(@PathVariable("typeid") String typeid) throws SQLException {
        return itemSaleV2Service.getSysTypePath(typeid);
    }

    @RequestMapping(value = "refresh/{typeid}", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public Object sysTypePathInvalidate(@PathVariable("typeid") String typeid) throws SQLException {
        itemSaleV2Service.invalidateSysTypePath(typeid);
        return null;
    }

}
