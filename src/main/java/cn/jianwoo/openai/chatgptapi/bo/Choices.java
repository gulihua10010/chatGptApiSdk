package cn.jianwoo.openai.chatgptapi.bo;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 19:13
 */
@Data
@ToString
@Builder
public class Choices implements Serializable
{
    private static final long serialVersionUID = 1203020411876310328L;
    private String text;

    private Integer index;

    private String logprobs;

    @JSONField(name = "finish_reason")
    private String finishReason;

    public String getText()
    {
        if (null == text)
        {
            return "";
        }
        return this.text;
    }
}
