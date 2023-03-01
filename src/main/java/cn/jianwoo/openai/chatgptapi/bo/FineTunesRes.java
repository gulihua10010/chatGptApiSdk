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
 * @date 2023-02-28 19:13
 */
@Data
@ToString
@Builder
public class FineTunesRes implements Serializable
{
    private static final long serialVersionUID = 3933030578633023126L;
    private String id;

    private String object;

    private String model;

    @JSONField(name = "created_at")
    private Long createdAt;

    private List<EventRes> events;

    @JSONField(name = "fine_tuned_model")
    private String fineTunedModel;

    private Hyperparams hyperparams;

    @JSONField(name = "organization_id")
    private String organizationId;

    @JSONField(name = "result_files")
    private List<String> resultFiles;

    private String status;

    @JSONField(name = "validation_files")
    private List<String> validationFiles;

    @JSONField(name = "training_files")
    private List<TrainingFiles> trainingFiles;

    @JSONField(name = "updated_at")
    private int updatedAt;


    @Data
    @ToString
    @Builder
    public static class Hyperparams implements Serializable
    {
        private static final long serialVersionUID = -2038826683714854343L;
        @JSONField(name = "batch_size")
        private Long batchSize;

        @JSONField(name = "learning_rate_multiplier")
        private BigDecimal learningRateMultiplier;

        @JSONField(name = "n_epochs")
        private Integer nEpochs;

        @JSONField(name = "prompt_loss_weight")
        private BigDecimal promptLossWeight;
    }

    @Data
    @ToString
    @Builder
    public static class TrainingFiles implements Serializable
    {
        private static final long serialVersionUID = 5832195282203971823L;
        private String id;

        private String object;

        private Long bytes;

        @JSONField(name = "created_at")
        private Long createdAt;

        private String filename;

        private String purpose;
    }
}
