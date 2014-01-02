package com.cplatform.sapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * User: cuikai Date: 13-8-15 Time: 下午3:37
 */
@Configuration
public class AppConfig {

    @Value("${WebApp.Root:/}")
    private String webRoot;

    @Value("${Search_Http_Url}")
    private String Search_Http_Url;

    @Value("${deposit.item_ids}")
    private String depositItemIds;

    @Value("${boss.request.productId}")
    private String productId;

    @Value("${boss.request.src}")
    private String reqSrc;

    @Value("${server.host}")
    private String serverHost;

    @Value("${auction.order.url}")
    private String auctionOrderUrl;

    @Value("${order.type.common_time}")
    private int commonOrderExpireTime;

    @Value("${order.type.spike_time}")
    private int spikeOrderExpireTime;

    @Value("${order.type.auction_time}")
    private int auctionOrderExpireTime;
    @Value("${order.type.special_time}")
    private int specialOrderExpireTime;

    @Value("${balance.query.url}")
    private String balanceQueryUrl;

    @Value("${union.member.url}")
    private String unionMemberUri;

    public String getUnionMemberUri() {
        return unionMemberUri;
    }

    public String getBalanceQueryUrl() {
        return balanceQueryUrl;
    }

    public int getCommonOrderExpireTime() {
        return commonOrderExpireTime;
    }

    public int getSpikeOrderExpireTime() {
        return spikeOrderExpireTime;
    }

    public int getAuctionOrderExpireTime() {
        return auctionOrderExpireTime;
    }

    public String getAuctionOrderUrl() {
        return auctionOrderUrl;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getProductId() {
        return productId;
    }

    public String getReqSrc() {
        return reqSrc;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public String getDepositItemIds() {
        return depositItemIds;
    }

    public String getSearch_Http_Url() {
        return Search_Http_Url;
    }

    public int getSpecialOrderExpireTime() {
        return specialOrderExpireTime;
    }
}
