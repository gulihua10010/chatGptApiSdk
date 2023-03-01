package cn.jianwoo.openai.chatgptapi.bo;

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
public class FileDetRes implements Serializable
{
    private static final long serialVersionUID = 2201393714953045971L;
    private String id;

    private String object;

    private Long bytes;

    @JSONField(name = "created_at")
    private Long createdAt;

    private String filename;

    private String purpose;
}
