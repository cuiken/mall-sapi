package com.cplatform.sapi.rest.product;

import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.TSysType;
import com.cplatform.sapi.service.product.TSysTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午1:12
 */
@Controller
@RequestMapping(value = "/api/v1/product")
public class CategoryController {

    private TSysTypeService typeService;
    private ItemSaleService itemSaleService;


    @RequestMapping(value = "category",method = RequestMethod.GET)
    @ResponseBody
    public List<TSysType> list(){
       return typeService.getByChannel(2L);

    }

    @RequestMapping(value = "ec_kill",method = RequestMethod.GET)
    @ResponseBody
    public List<ItemSale> getItemSale(){
        return itemSaleService.findKilledItem();
    }

    @Autowired
    public void setTypeService(TSysTypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setItemSaleService(ItemSaleService itemSaleService) {
        this.itemSaleService = itemSaleService;
    }
}
