package com.cplatform.sapi.data;

import com.cplatform.order.ActOrderGoodsInfo;
import com.cplatform.order.ActOrderInfo;
import com.cplatform.order.ActOrderPaymentInfo;
import com.cplatform.order.ActOrderStatus;
import com.cplatform.pay.PayOrderInfo;
import com.cplatform.pay.PaymentInfo;
import com.cplatform.sapi.DTO.CheckPayParam;
import com.cplatform.sapi.DTO.PayDTO;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.service.PayFormChoose;
import com.cplatform.sapi.util.Constants;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-11-26
 * Time: 下午5:15
 */
public class OrderData {

    public static CheckPayParam getCheckPayParam() {
        Member user = createMember(1L);

        CheckPayParam param = new CheckPayParam();
        param.setUser(user);
        param.setOrderId(102L);
        param.setBalance(100);
        param.setCoin(100);
        param.setScore(100);
        return param;
    }

    public static ActOrderInfo getActOrderInfo() {
        ActOrderInfo actOrderInfo = new ActOrderInfo();
        actOrderInfo.setPayStatus(ActOrderStatus.PAY_STATUS_UNPAID);
        actOrderInfo.setUserId(1L);

        ActOrderPaymentInfo actOrderPaymentInfo = new ActOrderPaymentInfo();
        actOrderPaymentInfo.setAmount(100);
        actOrderPaymentInfo.setCurrency(Constants.CURRENCY_SCORE);

        actOrderInfo.setPaymentInfos(Lists.newArrayList(actOrderPaymentInfo));

        ActOrderGoodsInfo goodsInfo = new ActOrderGoodsInfo();
        goodsInfo.setCount(1);
        goodsInfo.setPayPrice(100);
        actOrderInfo.setGoodsInfos(Lists.newArrayList(goodsInfo));

        return actOrderInfo;
    }

    public static List<PayOrderInfo> getPayOrderInfo() {
        PayOrderInfo payOrderInfo = new PayOrderInfo();
        payOrderInfo.setActOrderId(12L);
        payOrderInfo.setPaymentAmount(100);

        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setAmount(100);
        payOrderInfo.setPayments(Lists.newArrayList(paymentInfo));

        return Lists.newArrayList(payOrderInfo);
    }

    public static Member createMember(Long id) {
        Member member = new Member();
        member.setId(id);
        member.setTerminalId("1234565");
        member.setRedMember(1);
        return member;
    }

    public static TMemberAddress createMemberAddress() {
        TMemberAddress address = new TMemberAddress();
        address.setName("name1");
        address.setMobile("13551285475");
        address.setZipcode("12313");
        address.setRegion("320500");
        return address;
    }

    public static PayDTO randomPayData(String payForm, int otherAmount, int balanceAmount) {
        PayDTO payDTO = new PayDTO();
        payDTO.setPayForm(payForm);
        PayFormChoose payFormChoose = Enum.valueOf(PayFormChoose.class, StringUtils.upperCase(payForm));
        switch (payFormChoose) {
            case CASH_AND_BALANCE:
                payDTO.setBalance(otherAmount);
                break;
            case CASH_AND_COIN:
                payDTO.setCoin(otherAmount);
                break;
            case CASH_AND_SCORE:
                payDTO.setScore(otherAmount);
                break;
            case COIN_AND_BALANCE:
                payDTO.setCoin(otherAmount);
                payDTO.setBalance(balanceAmount);
                break;
            case ONLY_BALANCE:
                payDTO.setBalance(balanceAmount);
                break;
            case ONLY_COIN:
                payDTO.setCoin(otherAmount);
                break;
            case ONLY_SCORE:
                payDTO.setScore(otherAmount);
                break;
            case SCORE_AND_BALANCE:
                payDTO.setScore(otherAmount);
                payDTO.setBalance(balanceAmount);
                break;
        }
        return payDTO;
    }
}
