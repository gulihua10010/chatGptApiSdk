package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 19:13
 */
@Data
@ToString
@Builder
public class FineTunesReq implements Serializable
{
    private static final long serialVersionUID = 1782056672431751978L;
    /** (Required) The ID of an uploaded file that contains training data */
    @JSONField(name = "training_file")
    private String trainingFile;
    /** The ID of an uploaded file that contains validation data. */
    @JSONField(name = "validation_file")
    private String validationFile;
    /**
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
     * fine-tuned model created after 2022-04-21. To learn more about these models,
     */
    private String model;
    /**
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
     */
    @JSONField(name = "n_epochs")
    private Integer nEpochs;
    /**
     * The batch size to use for training. The batch size is the number of training examples used to train a single
     * forward and backward pass.
     */
    @JSONField(name = "batch_size")
    private Long batchSize;
    /**
     * The learning rate multiplier to use for training. The fine-tuning learning rate is the original learning rate
     * used for pretraining multiplied by this value.
     */
    @JSONField(name = "learning_rate_multiplier")
    private BigDecimal learningRateMultiplier;
    /**
     * The weight to use for loss on the prompt tokens. This controls how much the model tries to learn to generate the
     * prompt (as compared to the completion which always has a weight of 1.0), and can add a stabilizing effect to
     * training when completions are short.
     * 
     */
    @JSONField(name = "prompt_loss_weight")
    private BigDecimal promptLossWeight;
    /**
     * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the validation set at
     * the end of every epoch. These metrics can be viewed in the results file.
     */
    @JSONField(name = "compute_classification_metrics")
    private Boolean computeClassificationMetrics;

    /** The number of classes in a classification task. */
    @JSONField(name = "classification_n_classes")
    private Integer classificationNClasses;

    /** The positive class in binary classification. */
    @JSONField(name = "classification_positive_class")
    private String classificationPositiveClass;
    /**
     * If this is provided, we calculate F-beta scores at the specified beta values. The F-beta score is a
     * generalization of F-1 score. This is only used for binary classification.
     */
    @JSONField(name = "classification_betas")
    private List<BigDecimal> classificationBetas;
    /** A string of up to 40 characters that will be added to your fine-tuned model name. */
    private String suffix;

}
