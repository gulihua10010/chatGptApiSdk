package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 18:57
 */
@Data
@ToString
@Builder
public class CompletionRes implements Serializable
{
    private static final long serialVersionUID = 2572934731450584760L;
    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choices> choices;

    private Usage usage;

    public String getAnswer()
    {
        if (CollUtil.isEmpty(choices))
        {
            return null;
        }
        return choices.stream().map(Choices::getText).collect(Collectors.joining());

    }


    public String getChatContent()
    {
        if (CollUtil.isEmpty(choices))
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Choices c : choices)
        {
            if (null != c.getMessage())
            {
                sb.append(c.getMessage().getContent());
            }
            else if (null != c.getDelta())
            {
                sb.append(c.getDelta().getContent());
            }

        }
        return sb.toString();

    }

}
