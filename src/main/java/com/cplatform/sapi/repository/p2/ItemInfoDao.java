package com.cplatform.sapi.repository.p2;

import com.cplatform.dbhelp.DbHelper;
import com.cplatform.sapi.entity.p2.ItemExtNumInfo;
import com.cplatform.sapi.entity.p2.ItemInfo;
import com.google.code.ssm.api.*;
import com.google.code.ssm.api.format.Serialization;
import com.google.code.ssm.api.format.SerializationType;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 下午5:03
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Component
public class ItemInfoDao {

    private static final String SINGLE_NS = "sip.iteminfo";
    @Autowired
    DbHelper dbHelper;

    @Serialization(value = SerializationType.JSON)
    @ReadThroughSingleCache(namespace = SINGLE_NS, expiration = 3600)
    public ItemInfo findOne(@ParameterValueKeyProvider String id) throws SQLException {

        Validate.notNull(id, "商品ID不能为空");

        ItemInfo itemInfo = getItemFromDB(id);
        if (itemInfo == null)
            throw new SQLException("商品【" + id + "】不存在或已下架");
        return itemInfo;
    }

    @UpdateSingleCache(namespace = SINGLE_NS, expiration = 0)
    @ReturnDataUpdateContent
    public ItemInfo getItemFromDB(@ParameterValueKeyProvider String id) throws SQLException {
        String sql = new StringBuilder()
                .append("select tis.id g_id,\n")
                .append("       tis.cash_idgoods g_payment_cash,\n")
                .append("       tis.coin_idgoods g_payment_coin,\n")
                .append("       tis.score_idgoods g_payment_score,\n")
                .append("       tis.is_valid g_is_valid,\n")
                .append("       tis.status,\n")
                .append("       tis.iseckill g_iseckill,\n")
                .append("       tis.iseckill_price g_iseckill_price,\n")
                .append("       tis.org_id g_org_id,\n")
                .append("       tis.name g_name,\n")
                .append("       tis.short_name g_short_name,\n")
                .append("       tis.type_id g_type_id,\n")
                .append("       tis.brand g_brand,\n")
                .append("       tis.create_time g_create_time,\n")
                .append("       tis.update_time g_update_time,\n")
                .append("       tis.market_content g_market_content,\n")
                .append("       tis.img_path g_web_path,\n")
                .append("       tis.source g_source,\n")
                .append("       to_char(nvl(tis.market_price, 0), 'fm999999990.00') g_market_price,\n")
                .append("       to_char(nvl(tis.shop_price, 0), 'fm999999990.00') g_shop_price,\n")
                .append("       tis.remark g_remark,\n")
                .append("       tis.post_flag g_post_flag,\n")
                .append("       tis.item_mode g_item_mode,\n")
                .append("       tis.sale_start_time g_start_time,\n")
                .append("       tis.sale_stop_time g_stop_time,\n")
                .append("       tis.is_view g_is_view,\n")
                .append("       tis.warm_prompt g_warm_prompt,\n")
                .append("       tis.group_flag g_group_flag,\n")
                .append("       tis.store_id g_store_id,\n")
                .append("       to_char(nvl(tis.logistics_fee, 0), 'fm999999990.00') g_logistics_fee,\n")
                .append("       nvl(tis.logistics_fee_type, 0) g_logistics_fee_type,\n")
                .append("       nvl(tis.stock_num, 0) g_stock_num,\n")
                .append("       (select to_char(wm_concat(region_code)) region_code from t_item_sale_area_link area_link where tis.id = area_link.sale_id) g_region_code,\n")
                .append("       (select to_char(wm_concat(region_code)) region_codes from t_item_sale_user_area_link user_area_link where tis.id = user_area_link.sale_id) g_region_codes,\n")
                .append("       (select to_char(wm_concat(user_level)) user_levels from t_item_sale_user_level_link sale_user_level where tis.id = sale_user_level.sale_id) g_user_levels,\n")
                .append("       (select to_char(wm_concat(param_value)) property from t_item_param item_param where tis.id = item_param.item_id) g_property,\n")
                .append("       (select to_char(wm_concat(tag)) tags from t_item_tag item_tag where tis.id = item_tag.item_id) g_tags\n")
                .append("  from t_item_sale tis\n").append("   where tis.id=?").toString();

        return dbHelper.getBean(sql, ItemInfo.class, id);
    }

    @InvalidateSingleCache(namespace = SINGLE_NS)
    public void invalidateItemInfo(@ParameterValueKeyProvider String id) {
        // empty
    }

    public ItemExtNumInfo findExtNum(String id) throws SQLException {
        String sql = "select nvl(rank, 0) rank, nvl(sale_num, 0) sale_num, nvl(comment_num, 0) comment_num, " +
                "nvl(user_num, 0) user_num, nvl(collect_num, 0) collect_num, nvl(click_num, 0) click_num " +
                " from t_item_sale_ext where sale_id = ?";
        ItemExtNumInfo ext = dbHelper.getBean(sql, ItemExtNumInfo.class, id);

        sql = "select nvl(stock_num, 0) from t_item_sale where id = ?";
        String stockNum = dbHelper.queryScalar(sql, id);
        if (ext == null) {
            ext = new ItemExtNumInfo();
        }
        ext.setStockNum(Integer.valueOf(stockNum));

        return ext;
    }
}
