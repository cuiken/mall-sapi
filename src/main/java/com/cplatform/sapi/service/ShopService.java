package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.product.ShopInvoice;
import com.cplatform.sapi.repository.ShopInvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 上午11:34
 */
@Component
@Transactional
public class ShopService {

    private ShopInvoiceDao invoiceDao;

    public List<ShopInvoice> getInvoiceByShopId(Long shopId) {
        return invoiceDao.findBy("shopId", shopId);
    }

    @Autowired
    public void setInvoiceDao(ShopInvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }
}
