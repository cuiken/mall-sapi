package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.MarketGroupBuy;
import com.cplatform.sapi.entity.TItemSaleShopLink;
import com.cplatform.sapi.entity.TShop;
import com.cplatform.sapi.entity.product.ShopInvoice;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.ItemShopLinkDao;
import com.cplatform.sapi.repository.MarketGroupBuyDao;
import com.cplatform.sapi.repository.ShopDao;
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

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ItemShopLinkDao itemShopLinkDao;
    @Autowired
    private MarketGroupBuyDao marketGroupBuyDao;

    public MarketGroupBuy getMarketGroupBuy(Long id) {
        return marketGroupBuyDao.get(id);
    }

    public List<ShopInvoice> getInvoiceByShopId(Long shopId) {
        return invoiceDao.findBy("shopId", shopId);
    }

    public Page<TShop> searchShop(final Page<TShop> page, final List<PropertyFilter> filters) {
        return shopDao.findPage(page, filters);
    }

    public TShop getShop(Long id) {
        return shopDao.get(id);
    }

    public List<TItemSaleShopLink> getShopByItem(Long itemId) {
        return itemShopLinkDao.findBy("saleId", itemId);
    }

    public void initProxy(Object obj) {
        shopDao.initProxyObject(obj);
    }

    @Autowired
    public void setInvoiceDao(ShopInvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }
}
