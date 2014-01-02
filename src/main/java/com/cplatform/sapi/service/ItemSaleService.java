package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.EcKillDTO;
import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.product.ItemSaleDao;
import com.cplatform.sapi.repository.product.SysFileImageDao;
import com.cplatform.sapi.repository.product.TStoreDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai Date: 13-8-13 Time: 下午3:22
 */
@Component
@Transactional
public class ItemSaleService {

    @Autowired
    private ItemSaleDao itemSaleDao;
    @Autowired
    private TStoreDao tStoreDao;
    @Autowired
    private SysFileImageDao sysFileImageDao;


    public ItemSaleDataDTO convert_v2_to_v1_DTO(String itemId) throws Exception{
        return itemSaleDao.getItemById(itemId);
    }

    public Page<ItemSale> searchItemSale(final Page<ItemSale> page, final List<PropertyFilter> filters) {
        return itemSaleDao.findPage(page, filters);
    }

    public ItemSale getItemSale(Long id) {
        return itemSaleDao.get(id);
    }

    public TStore getTStore(Long id) {
        return tStoreDao.get(id);
    }

    public List<SysFileImg> getItemSaleImgs(Long id) {
        return sysFileImageDao.getByBsIdAndBsKey("ITEM_PIC", id);
    }

    public GoodStatus getGoodStatus(List<TActOrderGoods> goodses, String userId) {
        GoodStatus status = GoodStatus.NOT_BUY;
        if (goodses.size() == 0) { // 未购买
            return GoodStatus.NOT_BUY;
        } else {
            for (TActOrderGoods goods : goodses) {
                TActOrder order = goods.getOrder();
                if (Long.valueOf(userId).equals(order.getUserId())) {
                    if (order.getStatus() == 2) {
                        return GoodStatus.BUY_OK;
                    } else if (order.getStatus() == 1) {
                        status = GoodStatus.NOT_PAY;
                        continue;
                    }
                }
            }
        }
        return status;
    }

    public EcKillDTO converToDTO(ItemSale itemSale) {
        EcKillDTO dto = BeanMapper.map(itemSale, EcKillDTO.class);
        List<String> thumbs = Lists.newArrayList();
        List<SysFileImg> imgs = getItemSaleImgs(itemSale.getId());
        for (SysFileImg fileImg : imgs) {
            thumbs.add(fileImg.getFileName());
        }
        if (StringUtils.isNotBlank(itemSale.getImgPath())) {
            thumbs.add(itemSale.getImgPath());
        }
        dto.setThumbs(thumbs);
        return dto;
    }

    public enum GoodStatus {

        NOT_BUY("0", "未购买"),
        NOT_PAY("1", "代付款"),
        BUY_OK("2", "已付款");

        GoodStatus(String flag, String msg) {
            this.flag = flag;
            this.msg = msg;
        }

        private String flag;
        private String msg;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
