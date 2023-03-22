package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author gulihua
 * @Description
 * @date 2023-03-23 01:15
 */
@Data
@ToString
@Builder
public class CreditGrantsRes implements Serializable {
    private static final long serialVersionUID = 2334542692107992629L;

    private String object;
    /**
     * 账户总金额(单位:美元)
     */
    @JSONField(name = "total_granted")
    private BigDecimal totalGranted;
    /**
     * 账户使用金额(单位:美元)
     */
    @JSONField(name = "total_used")
    private BigDecimal totalUsed;
    /**
     * 账户剩余金额(单位:美元)
     */
    @JSONField(name = "total_available")
    private BigDecimal totalAvailable;
    /**
     * 账户余额明细
     */
    private Grants grants;

    @Data
    @ToString
    @Builder
    public static class Grants {
        private String object;
        @JSONField(name = "data")
        private List<Datum> data;
    }


    @Data
    @ToString
    @Builder
    public static class Datum {
        private String object;
        private String id;
        /**
         * 账户赠送金额(单位:美元)
         */
        @JSONField(name = "grant_amount")
        private BigDecimal grantAmount;
        /**
         * 账户使用金额(单位:美元)
         */
        @JSONField(name = "used_amount")
        private BigDecimal usedAmount;
        /**
         * 生效时间
         */
        @JSONField(name = "effective_at")
        private Long effectiveAt;
        /**
         * 过期时间
         */
        @JSONField(name = "expires_at")
        private Long expiresAt;
    }
}
