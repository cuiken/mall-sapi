package com.cplatform.sapi.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-8-21 下午4:11
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class BaseTransBean implements Serializable {

    public BaseTransBean(boolean genCertCode) {
        if (genCertCode) genCertCode();
    }

    public void genCertCode() {
        this.certCode = RandomStringUtils.randomNumeric(6);
    }

    private String certCode;

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }
}
