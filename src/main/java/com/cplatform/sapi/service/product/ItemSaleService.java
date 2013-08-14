package com.cplatform.sapi.service.product;

import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.product.ItemSaleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午3:22
 */
@Component
@Transactional
public class ItemSaleService {

    private ItemSaleDao itemSaleDao;


    public Page<ItemSale> searchItemSale(final Page<ItemSale> page, final List<PropertyFilter> filters) {
        return itemSaleDao.findPage(page, filters);
    }

    public ItemSale getItemSale(Long id) {
        return itemSaleDao.get(id);
    }

    @Autowired
    public void setItemSaleDao(ItemSaleDao itemSaleDao) {
        this.itemSaleDao = itemSaleDao;
    }
}
