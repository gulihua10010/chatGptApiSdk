package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 23:42
 */
@Data
@ToString
@Builder
public class EventRes implements Serializable
{
    private static final long serialVersionUID = -2682859742737504203L;
    private String object;

    @JSONField(name = "created_at")
    private Long createdAt;

    private String level;

    private String message;

}
