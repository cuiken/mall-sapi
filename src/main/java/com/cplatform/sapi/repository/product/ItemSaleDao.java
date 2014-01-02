package com.cplatform.sapi.repository.product;

import com.cplatform.sapi.DTO.ItemInfo_v2Tov1DTO;
import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.entity.p2.ItemInfo;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import com.cplatform.sapi.service.ItemSaleV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午3:04
 */
@Component
public class ItemSaleDao extends HibernateDao<ItemSale, Long> {

    @Autowired
    private ItemSaleV2Service itemSaleV2Service;

    /**
     * 兼容第一版数据结构
     *
     * @param itemId
     */
    public ItemSaleDataDTO getItemById(String itemId) throws Exception {

        ItemSaleDataDTO dto = new ItemSaleDataDTO();
        ItemInfo itemInfo = itemSaleV2Service.getItemInfo(itemId, true);

        JsonMapper mapper = JsonMapper.buildNormalMapper();
        String v2Json = mapper.toJson(itemInfo);
        ItemInfo_v2Tov1DTO v2ItemDTO = mapper.fromJson(v2Json, ItemInfo_v2Tov1DTO.class);
        dto.setItem(BeanMapper.map(v2ItemDTO, ItemSaleDataDTO.Item.class));
        dto.setItemPrice(BeanMapper.mapList(v2ItemDTO.getItemPrice(), ItemSaleDataDTO.ItemPrice.class));
        dto.setMarketGoodsProperty(itemInfo.getMarketGoodsProperty());
        if (itemInfo.getStoreInfo() != null) {
            dto.getItem().setStoreName(itemInfo.getStoreInfo().getName());
            dto.getItem().setStoreShortName(itemInfo.getStoreInfo().getShortName());
        }
        if (itemInfo.getItemPayment() != null) {
            dto.getItem().setBillPay(itemInfo.getItemPayment().getBillPay());
            dto.getItem().setPayType(itemInfo.getItemPayment().getPayType());
            dto.getItem().setDeliveryPay(itemInfo.getItemPayment().getDeliveryPay());
        }
        if (itemInfo.getItemExtNumInfo() != null) {
            dto.getItem().setRank(itemInfo.getItemExtNumInfo().getRank());
            dto.getItem().setClicknum(itemInfo.getItemExtNumInfo().getClickNum());
            dto.getItem().setUsernum(itemInfo.getItemExtNumInfo().getUserNum());
            dto.getItem().setStocknum(itemInfo.getItemExtNumInfo().getStockNum());
            dto.getItem().setSalenum(itemInfo.getItemExtNumInfo().getSaleNum());
            dto.getItem().setCollectnum(itemInfo.getItemExtNumInfo().getCollectNum());
            dto.getItem().setCommentnum(itemInfo.getItemExtNumInfo().getCommentNum());
        }

        return dto;
    }
}
