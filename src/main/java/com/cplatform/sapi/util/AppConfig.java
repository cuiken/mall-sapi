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

	@Value("${interface.iteminfo}")
	private String interfaceItemInfo;

	@Value("${Search_Http_Url}")
	private String Search_Http_Url;

	@Value("${httpclient.So_Timeout}")
	private Integer So_Timeout;

	@Value("${httpclient.Connection_Manager_Timeout}")
	private Integer Connection_Manager_Timeout;

	@Value("${order.expiretime:172800}")
	private int orderExpireTime;

	@Value("${jms.sms.destination:q_sms_mt_act_2}")
	private String jmsSmsDestination;

	@Value("${jms.sms.spcode:106585854}")
	private String jmsSmsSpcode;

	@Value("${template.order.pay}")
	private String templateOrderPay;

	@Value("${deposit.item_ids}")
	private String depositItemIds;

	public String getTemplateOrderPay() {
		return templateOrderPay;
	}

	public void setTemplateOrderPay(String templateOrderPay) {
		this.templateOrderPay = templateOrderPay;
	}

	public String getJmsSmsSpcode() {
		return jmsSmsSpcode;
	}

	public void setJmsSmsSpcode(String jmsSmsSpcode) {
		this.jmsSmsSpcode = jmsSmsSpcode;
	}

	public String getJmsSmsDestination() {
		return jmsSmsDestination;
	}

	public void setJmsSmsDestination(String jmsSmsDestination) {
		this.jmsSmsDestination = jmsSmsDestination;
	}

	public int getOrderExpireTime() {
		return orderExpireTime;
	}

	public void setOrderExpireTime(int orderExpireTime) {
		this.orderExpireTime = orderExpireTime;
	}

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

	public String getDepositItemIds() {
		return depositItemIds;
	}

}
