package cn.jianwoo.openai.chatgptapi.bo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-03-02 22:13
 */
@Data
@ToString
@Builder
public class MessageReq implements Serializable
{
    private static final long serialVersionUID = 7342715261030858658L;
    private String role;
    private String content;
}
