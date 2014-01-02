package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.ShopInvoiceDTO;
import com.cplatform.sapi.entity.MarketGroupBuy;
import com.cplatform.sapi.entity.TItemSaleShopLink;
import com.cplatform.sapi.entity.TShop;
import com.cplatform.sapi.entity.product.ShopInvoice;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ShopService;
import com.cplatform.sapi.util.MediaTypes;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 上午11:44
 */

@Controller
@RequestMapping("/api/v1/shop")
public class ShopController {

    private static final String PAGE_SIZE = "10";

    private ShopService shopService;

    @RequestMapping(value = "invoice")
    @ResponseBody
    public ShopInvoiceDTO invoice(@RequestParam(value = "shopId") Long shopId) {

        List<ShopInvoice> invoices = shopService.getInvoiceByShopId(shopId);
        ShopInvoiceDTO invoiceDTO = new ShopInvoiceDTO();
        invoiceDTO.setData(BeanMapper.mapList(invoices, ShopInvoiceDTO.Data.class));
        return invoiceDTO;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public List<TShop> shops(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                             @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
                             @RequestParam(value = "sortType", defaultValue = "auto") String sortType,
                             HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        Page<TShop> page = new Page<TShop>();

        return shopService.searchShop(page, filters).getResult();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public TShop shop(@PathVariable("id") Long id) {
        TShop entity = shopService.getShop(id);
        shopService.initProxy(entity);
        return entity;
    }

    @RequestMapping(value = "query", produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public List<TShop> shopsByItem(@RequestParam(value = "itemId", required = false) Long itemId,
                                   @RequestParam(value = "groupId", required = false) Long groupId) {
        List<TShop> shops = Lists.newArrayList();
        if (itemId == null && groupId != null) {
            MarketGroupBuy groupBuy = shopService.getMarketGroupBuy(groupId);
            itemId = groupBuy.getProductId();
        }
        List<TItemSaleShopLink> links = shopService.getShopByItem(itemId);
        for (TItemSaleShopLink link : links) {
            TShop entity = shopService.getShop(link.getShopId());
            shopService.initProxy(entity);
            shops.add(entity);
        }
        return shops;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}
