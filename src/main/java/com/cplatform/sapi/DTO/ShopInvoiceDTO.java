package com.cplatform.sapi.DTO;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 下午12:10
 */
public class ShopInvoiceDTO {

    private String flag;
    private String msg;
    private List<Data> data = Lists.newArrayList();

    public String getFlag() {
        return "0";
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return "success";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private Long id;

        private int shopClass;

        private Long shopId;

        private Long invoiceId;

        private String invoiceName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getShopClass() {
            return shopClass;
        }

        public void setShopClass(int shopClass) {
            this.shopClass = shopClass;
        }

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
        }

        public Long getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(Long invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getInvoiceName() {
            return invoiceName;
        }

        public void setInvoiceName(String invoiceName) {
            this.invoiceName = invoiceName;
        }
    }
}
