package com.cplatform.sapi.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "T_SHOP_INVOICE")
@Entity
public class ShopInvoice {

	private Long id;

	private int shopClass;

	private Long shopId;

	private Long invoiceId;

	private String invoiceName;

	private List<Long> invoiceIds = new ArrayList<Long>();

	/**
	 * 获取 id
	 * 
	 * @return id
	 */
	@SequenceGenerator(name = "seq_item", sequenceName = "SEQ_SHOP_INVOICE")
	@Id
	@GeneratedValue(generator = "seq_item")
	@JsonProperty
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

	@Transient
	public List<Long> getInvoiceIds() {
		return invoiceIds;
	}

	@Transient
	public void setInvoiceIds(List<Long> invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

}
