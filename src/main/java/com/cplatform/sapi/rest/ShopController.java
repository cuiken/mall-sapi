package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.ShopInvoiceDTO;
import com.cplatform.sapi.entity.product.ShopInvoice;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 上午11:44
 */

@Controller
@RequestMapping("/api/v1/shop")
public class ShopController {

    private ShopService shopService;

    @RequestMapping(value = "invoice")
    @ResponseBody
    public ShopInvoiceDTO invoice(@RequestParam(value = "shopId") Long shopId) {

        List<ShopInvoice> invoices = shopService.getInvoiceByShopId(shopId);
        ShopInvoiceDTO invoiceDTO = new ShopInvoiceDTO();
        invoiceDTO.setData(BeanMapper.mapList(invoices, ShopInvoiceDTO.Data.class));
        return invoiceDTO;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}
