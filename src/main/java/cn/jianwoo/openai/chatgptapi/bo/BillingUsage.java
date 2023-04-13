package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gulihua
 * @Description
 * @date 2023-04-13 16:48
 */
@Data
@ToString
@Builder
public class BillingUsage
{

    @JSONField(name = "object")
    private String object;
    /**
     * 账号金额消耗明细
     */
    @JSONField(name = "daily_costs")
    private List<DailyCost> dailyCosts;
    /**
     * 总使用金额：美分
     */
    @JSONField(name = "total_usage")
    private BigDecimal totalUsage;

    @Data
    @ToString
    @Builder
    public static class DailyCost
    {
        /**
         * 时间戳
         */
        @JSONField(name = "timestamp")
        private long timestamp;
        /**
         * 模型消耗金额详情
         */
        @JSONField(name = "line_items")
        private List<LineItem> lineItems;
    }

    @Data
    @ToString
    @Builder
    public static class LineItem
    {
        /**
         * 模型名称
         */
        private String name;
        /**
         * 消耗金额
         */
        private BigDecimal cost;
    }

}
