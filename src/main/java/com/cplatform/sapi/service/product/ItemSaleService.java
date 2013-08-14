package com.cplatform.sapi.service.product;

import com.cplatform.sapi.entity.product.ItemSale;
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

    public List<ItemSale> findKilledItem(){
        return itemSaleDao.createQuery("from ItemSale item where item.isValid=1 and item.iseckill=1 ").list();
    }

    @Autowired
    public void setItemSaleDao(ItemSaleDao itemSaleDao) {
        this.itemSaleDao = itemSaleDao;
    }
}
