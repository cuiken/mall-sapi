package com.cplatform.sapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.order.TActOrderGoodsDao;
import com.cplatform.sapi.repository.product.ItemSaleDao;
import com.cplatform.sapi.repository.product.TStoreDao;

/**
 * User: cuikai Date: 13-8-13 Time: 下午3:22
 */
@Component
@Transactional
public class ItemSaleService {

	private ItemSaleDao itemSaleDao;

	private TActOrderGoodsDao actOrderGoodsDao;

	private TStoreDao tStoreDao;

	// public List<TActOrderGoods> findOrderGoodsById(Long goodsId) {
	// return actOrderGoodsDao.findBy("itemSale.id", goodsId);
	// }

	public Page<ItemSale> searchItemSale(final Page<ItemSale> page, final List<PropertyFilter> filters) {
		return itemSaleDao.findPage(page, filters);
	}

	public ItemSale getItemSale(Long id) {
		return itemSaleDao.get(id);
	}

	public void initProxy(Object obj) {
		itemSaleDao.initProxyObject(obj);
	}

	public TStore getTStore(Long id) {
		return tStoreDao.get(id);
	}

	@Autowired
	public void setItemSaleDao(ItemSaleDao itemSaleDao) {
		this.itemSaleDao = itemSaleDao;
	}

	@Autowired
	public void setActOrderGoodsDao(TActOrderGoodsDao actOrderGoodsDao) {
		this.actOrderGoodsDao = actOrderGoodsDao;
	}

	@Autowired
	public void settStoreDao(TStoreDao tStoreDao) {
		this.tStoreDao = tStoreDao;
	}

}
