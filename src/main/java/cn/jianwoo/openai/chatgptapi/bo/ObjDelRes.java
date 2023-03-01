package cn.jianwoo.openai.chatgptapi.bo;

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
public class ObjDelRes implements Serializable
{
    private static final long serialVersionUID = 4158216211948315224L;
    private String id;

    private String object;

    private Boolean deleted;
}
