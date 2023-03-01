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
 * @date 2023-02-28 23:42
 */
@Data
@ToString
@Builder
public class ModerationsRes implements Serializable
{

    private static final long serialVersionUID = -7626651671774441526L;
    private String id;

    private String model;

    private List<Results> results;

    @Data
    @ToString
    @Builder
    public static class Categories implements Serializable
    {
        private static final long serialVersionUID = -3594412612848243437L;
        private Boolean hate;

        @JSONField(name = "hate/threatening")
        private Boolean hateThreatening;

        @JSONField(name = "self-harm")
        private Boolean selfHarm;

        private Boolean sexual;

        @JSONField(name = "sexual/minors")
        private Boolean sexualMinors;

        private Boolean violence;

        @JSONField(name = "violence/graphic")
        private Boolean violenceGraphic;

    }

    @Data
    @ToString
    @Builder
    public static class CategoryScores implements Serializable
    {
        private static final long serialVersionUID = 4124486670170898185L;
        private BigDecimal hate;

        @JSONField(name = "hate/threatening")
        private BigDecimal hateThreatening;
        @JSONField(name = "self-harm")
        private BigDecimal selfHarm;

        private BigDecimal sexual;

        @JSONField(name = "sexual/minors")
        private BigDecimal sexualMinors;

        private BigDecimal violence;

        @JSONField(name = "violence/graphic")
        private BigDecimal violenceGraphic;

    }

    @Data
    @ToString
    @Builder
    public static class Results implements Serializable
    {
        private static final long serialVersionUID = -181955305218248039L;
        private Categories categories;

        @JSONField(name = "category_scores")
        private CategoryScores categoryScores;

        private Boolean flagged;
    }

}
