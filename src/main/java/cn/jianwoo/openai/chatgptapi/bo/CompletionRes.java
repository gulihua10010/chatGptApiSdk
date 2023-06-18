package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

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
    /** 是否已经完成 */
    private Boolean done;
    /** 是否成功 */
    private Boolean isSuccess;
    /** 失败详情 */
    private HttpFailedBO failed;

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
            return "";
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

    public String getFunctionCall()
    {
        if (CollUtil.isEmpty(choices))
        {
            return "";
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

    public JSONObject getFunctionArgs()
    {
        JSONObject args = new JSONObject();
        if (CollUtil.isEmpty(choices))
        {
            return args;
        }
        Choices c = choices.get(0);
        if (null == c.getMessage())
        {
            return args;
        }
        MessageReq messageReq = c.getMessage();
        if (null == messageReq || null == messageReq.getFunctionCall())
        {
            return args;
        }
        return JSON.parseObject(messageReq.getFunctionCall().getArguments());

    }
}
