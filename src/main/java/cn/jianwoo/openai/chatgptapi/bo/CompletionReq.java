package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 18:57
 */
@Data
@ToString
@Builder
public class CompletionReq implements Serializable
{
    private static final long serialVersionUID = 881176059503224136L;
    /**
     * (Required) ID of the model to use. You can use the List models API to see all of your available models, or see
     * our ModelRes overview for descriptions of them.
     */
    private String model;

    /** The messages to generate chat completions for, in the chat format. */
    private List<MessageReq> messages;

    /**
     * The prompt(s) to generate completions for, encoded as a string, array of strings, array of tokens, or array of
     * token arrays.
     * 
     */
    private String prompt;

    /** The maximum number of tokens to generate in the completion. */
    @JSONField(name = "max_tokens")
    @Builder.Default
    private Integer maxTokens = 2048;

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while
     * lower values like 0.2 will make it more focused and deterministic.
     */
    @Builder.Default
    private BigDecimal temperature = BigDecimal.ZERO;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
     * considered.
     */
    @JSONField(name = "top_p")
    private BigDecimal topP;

    /** How many completions to generate for each prompt. */
    private Integer n;

    /**
     * Whether to stream back partial progress. If set, tokens will be sent as data-only server-sent events as they
     * become available, with the stream terminated by a data: [DONE] message.
     */
    private Boolean stream;

    /**
     * Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens. For example, if
     * logprobs is 5, the API will return a list of the 5 most likely tokens. The API will always return the logprob of
     * the sampled token, so there may be up to logprobs+1 elements in the response
     */
    private String logprobs;

    /**
     * Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop
     * sequence.
     */
    private String stop;
    /** The suffix that comes after a completion of inserted text. */
    private String suffix;

    /** Echo back the prompt in addition to the completion */
    private String echo;
    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far,
     * increasing the model's likelihood to talk about new topics.
     */
    @JSONField(name = "presence_penalty")
    private BigDecimal presencePenalty;
    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so
     * far, decreasing the model's likelihood to repeat the same line verbatim.
     */
    @JSONField(name = "frequency_penalty")
    private BigDecimal frequencyPenalty;
    /**
     * Generates best_of completions server-side and returns the "best" (the one with the highest log probability per
     * token). Results cannot be streamed.
     */
    @JSONField(name = "best_of")
    private String bestOf;
    /** Modify the likelihood of specified tokens appearing in the completion. */
    @JSONField(name = "logit_bias")
    private Map<String, Object> logitBias;
    /** A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. */
    private String user;

    /**
     * The input text to use as a starting point for the edit.
     *
     */
    private String input;

    /**
     * (Required in Edit)The instruction that tells the model how to edit the prompt.
     *
     */
    private String instruction;

    private transient Boolean isReTry;

    public Boolean getIsReTry()
    {
        if (null == isReTry)
        {
            this.isReTry = false;
        }
        return this.isReTry;
    }
}
