package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 19:14
 */
@Data
@ToString
@Builder
public class Usage implements Serializable
{

    private static final long serialVersionUID = 4102065520113901923L;
    @JSONField(name = "prompt_tokens")
    private Integer promptTokens;

    @JSONField(name = "completion_tokens")
    private Integer completionTokens;

    @JSONField(name = "total_tokens")
    private Integer totalTokens;
}
