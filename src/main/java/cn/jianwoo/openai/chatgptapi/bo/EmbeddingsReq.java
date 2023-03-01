package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 18:57
 */
@Data
@ToString
@Builder
public class EmbeddingsReq implements Serializable
{
    private static final long serialVersionUID = -4876536562204868695L;
    /**
     * ID of the model to use. You can use the List models API to see all of your available models, or see our ModelRes
     * overview for descriptions of them.
     */
    private String model;

    /** A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. */
    private String user;

    /**
     * Input text to get embeddings for, encoded as a string or array of tokens. To get embeddings for multiple inputs
     * in a single request, pass an array of strings or array of token arrays. Each input must not exceed 8192 tokens in
     * length.
     *
     */
    private String input;

}
