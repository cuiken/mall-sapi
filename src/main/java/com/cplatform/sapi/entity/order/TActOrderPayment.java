package com.cplatform.sapi.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * 业务订单内应付款信息.
 * <p/>
 * 业务订单与应付款信息呈1:n关系
 * <p/>
 * Copyright: Copyright (c) Jun 13, 2013 3:36:06 PM
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 *
 * @author zhangyinf@c-platform.com
 * @version 1.0.0
 */
@Entity
@Table(name = "t_act_order_payment")
public class TActOrderPayment {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2680122655463160355L;

    private String channal;

    private String amount;

    private String currency;

    /**
     * 记录id
     */
    private long id;

    private TActOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_order_id")
    @JsonIgnore
    public TActOrder getOrder() {
        return order;
    }

    public void setOrder(TActOrder order) {
        this.order = order;
    }

    /**
     * 获取 id
     *
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "seq_act_order_sub")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    /**
     * 设置 id
     *
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Column(name = "CHANNAL")
    public String getChannal() {
        return channal;
    }

    public void setChannal(String channal) {
        this.channal = channal;
    }

    @Column(name = "AMOUNT")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
