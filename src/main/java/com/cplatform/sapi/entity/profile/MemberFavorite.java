package com.cplatform.sapi.entity.profile;

import com.cplatform.sapi.entity.IdEntity;
import com.cplatform.sapi.entity.product.ItemSale;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * 用户收藏 entity
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午11:21
 */
@Entity
@Table(name = "T_MEMBER_FAVORITE")
public class MemberFavorite extends IdEntity {

    private ItemSale itemSale;
    private String updateTime;
    private Long favoriteType;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "favorite_id")
    public ItemSale getItemSale() {
        return itemSale;
    }

    public void setItemSale(ItemSale itemSale) {
        this.itemSale = itemSale;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(Long favoriteType) {
        this.favoriteType = favoriteType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
