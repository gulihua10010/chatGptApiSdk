package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-04-13 16:44
 */
@Data
@ToString
@Builder
public class Subscription implements Serializable
{
    private static final long serialVersionUID = -6850948420912930882L;

    @JSONField(name = "object")
    private String object;
    @JSONField(name = "has_payment_method")
    private boolean hasPaymentMethod;
    @JSONField(name = "canceled")
    private boolean canceled;
    @JSONField(name = "canceled_at")
    private Object canceledAt;
    @JSONField(name = "delinquent")
    private Object delinquent;
    @JSONField(name = "access_until")
    private long accessUntil;
    @JSONField(name = "soft_limit")
    private long softLimit;
    @JSONField(name = "hard_limit")
    private long hardLimit;
    @JSONField(name = "system_hard_limit")
    private long systemHardLimit;
    @JSONField(name = "soft_limit_usd")
    private double softLimitUsd;
    @JSONField(name = "hard_limit_usd")
    private double hardLimitUsd;
    @JSONField(name = "system_hard_limit_usd")
    private double systemHardLimitUsd;
    @JSONField(name = "plan")
    private Plan plan;
    @JSONField(name = "account_name")
    private String accountName;
    @JSONField(name = "po_number")
    private Object poNumber;
    @JSONField(name = "billing_email")
    private Object billingEmail;
    @JSONField(name = "tax_ids")
    private Object taxIds;
    @JSONField(name = "billing_address")
    private Object billingAddress;
    @JSONField(name = "business_address")
    private Object businessAddress;

    @Data
    @ToString
    @Builder
    public static class Plan
    {
        private String title;
        private String id;
    }

}
