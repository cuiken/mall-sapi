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

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }
}
