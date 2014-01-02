package com.cplatform.sapi.rest;

import com.cplatform.sapi.entity.p2.ItemInfo;
import com.cplatform.sapi.service.ItemSaleV2Service;
import com.cplatform.sapi.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 上午10:00
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Controller
@RequestMapping(value = "/api/v2/product")
public class ProductV2Controller {

    @Autowired
    private ItemSaleV2Service itemSaleV2Service;

    @RequestMapping(value = "detail/{itemId}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ItemInfo detail(@PathVariable("itemId") String id,
                         @RequestParam(defaultValue = "false") Boolean ext) throws SQLException {
        return itemSaleV2Service.getItemInfo(id, ext);
    }

    @RequestMapping(value = "refresh/item/{itemId}", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public Object itemInfoInvalidate(@PathVariable("itemId") String itemId) throws SQLException {
        itemSaleV2Service.invalidateItemInfo(itemId);
        return null;
    }

    @RequestMapping(value = "refresh/store/{storeId}", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public Object itemStoreInfoInvalidate(@PathVariable("storeId") String storeId) throws SQLException {
        itemSaleV2Service.invalidateItemStoreInfo(storeId);
        return null;
    }

}
