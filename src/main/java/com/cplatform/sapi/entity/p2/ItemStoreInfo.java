package com.cplatform.sapi.entity.p2;

import java.io.Serializable;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 下午5:37
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class ItemStoreInfo implements Serializable {

    private String name;

    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
