package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.p2.ItemInfo;
import com.cplatform.sapi.entity.p2.ItemPriceInfo;
import com.cplatform.sapi.entity.p2.SysType;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.repository.p2.*;
import com.cplatform.sapi.repository.product.MarketGoodsDao;
import com.cplatform.sapi.repository.product.SysFileImageDao;
import com.cplatform.sapi.util.Constants;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 下午3:18
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Service
@Transactional
public class ItemSaleV2Service {

    private static final String PREFIX_IMAGE = "ITEM_PIC";
    @Autowired
    SysTypeDao sysTypeDao;

    @Autowired
    ItemInfoDao itemInfoDao;

    @Autowired
    ItemPriceInfoDao itemPriceInfoDao;

    @Autowired
    ItemStoreDao itemStoreDao;

    @Autowired
    ItemPaymentDao itemPaymentDao;

    @Autowired
    private SysFileImageDao sysFileImageDao;
    @Autowired
    private MarketGoodsDao marketGoodsDao;

    public List<SysType> getSysTypePath(String sysTypeId) throws SQLException {
        List<SysType> sysTypes = new ArrayList<SysType>();
        SysType sysType = sysTypeDao.findOne(sysTypeId);
        while (sysType != null) {
            sysTypes.add(0, sysType);
            sysType = sysTypeDao.findOne(sysType.getpId());
        }
        return sysTypes;
    }

    public void invalidateSysTypePath(String sysTypeId) {
        sysTypeDao.invalidateSysType(sysTypeId);
    }

    public void invalidateItemInfo(String itemId) {
        itemInfoDao.invalidateItemInfo(itemId);
        itemPriceInfoDao.invalidateItemPriceInfo(itemId);
        itemPaymentDao.invalidateItemPayment(itemId);
        sysFileImageDao.invalidateFileImage(PREFIX_IMAGE, Long.valueOf(itemId));
    }

    public void invalidateItemStoreInfo(String storeId) {
        itemStoreDao.invalidateItemStoreInfo(storeId);
    }

    public ItemInfo getItemInfo(String itemId, boolean isGetExt) throws SQLException {
        ItemInfo item = itemInfoDao.findOne(itemId);
        List<SysFileImg> itemSaleImgs = sysFileImageDao.getByBsIdAndBsKey(PREFIX_IMAGE, Long.valueOf(item.getgId()));
        List<String> imgs = Lists.newArrayList();
        for (SysFileImg fileImg : itemSaleImgs) {
            imgs.add(fileImg.getFileName());
        }
        if (StringUtils.isNotBlank(item.getgWebPath())) {
            imgs.add(item.getgWebPath());
        }
        item.setImages(imgs);
        if (Constants.PANIC_BUYING.equals(item.getgIseckill())) {
            item.setMarketGoodsProperty(marketGoodsDao.getByGoodsId(item.getgId()));
        }
        item.setItemPrices(itemPriceInfoDao.findPrices(itemId));
        item.setStoreInfo(itemStoreDao.findOne(item.getgStoreId()));
        item.setItemPayment(itemPaymentDao.findOne(itemId));

        // systype
        item.setSysTypePath(getSysTypePath(item.getgTypeId()));
        SysType type = sysTypeDao.findOne(item.getgTypeId());
        if (type != null)
            item.setgTypeName(type.getName());

        // ignore item.setItemProperties(...);

        // minprice
        List<String> prices = new ArrayList<String>();
        prices.add(item.getgShopPrice());
        prices.add(item.getgMarketPrice());
        for (ItemPriceInfo itemPriceInfo : item.getItemPrices()) {
            prices.add(itemPriceInfo.getPrice());
        }
        item.setgMinPrice(minPrice(prices));

        if (isGetExt) {
            item.setItemExtNumInfo(itemInfoDao.findExtNum(itemId));
        }
        return item;
    }

    private String minPrice(List<String> prices) {
        BigDecimal min = new BigDecimal(Long.MAX_VALUE);
        String minStr = null;
        for (String s : prices) {
            if (s == null) continue;
            BigDecimal pri = new BigDecimal(s);
            if (min.compareTo(pri) > 0) {
                min = pri;
                minStr = s;
            }
        }
        return minStr;
    }
}
