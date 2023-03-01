package cn.jianwoo.openai.chatgptapi.bo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import sun.nio.cs.ext.Big5;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 18:57
 */
@Data
@ToString
@Builder
public class EmbeddingsRes implements Serializable
{
    private static final long serialVersionUID = 7464674452424003392L;
    private String object;

    private List<Data> data;

    private String model;

    private Usage usage;

    @lombok.Data
    @ToString
    @Builder
    public static class Data implements Serializable
    {
        private static final long serialVersionUID = 4926704717764854168L;
        private String object;

        private Integer index;

        private List<BigDecimal> embedding;
    }
}
