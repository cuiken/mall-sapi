package com.cplatform.sapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午3:37
 */
@Configuration
public class AppConfig {
    @Value("${WebApp.Root:/}")
    private String webRoot;

    @Value("${interface.iteminfo}")
    private String interfaceItemInfo;

    @Value("${Search_Http_Url}")
    private String Search_Http_Url;

    @Value("${httpclient.So_Timeout}")
    private Integer So_Timeout;

    @Value("${httpclient.Connection_Manager_Timeout}")
    private Integer Connection_Manager_Timeout;


    public String getWebRoot() {
        return webRoot;
    }

    public String getInterfaceItemInfo() {
        return interfaceItemInfo;
    }

    public String getSearch_Http_Url() {
        return Search_Http_Url;
    }

    public Integer getConnection_Manager_Timeout() {
        return Connection_Manager_Timeout;
    }

    public Integer getSo_Timeout() {
        return So_Timeout;
    }
}
