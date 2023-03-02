package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 23:42
 */
@Data
@ToString
@Builder
public class AudioRes implements Serializable
{
    private static final long serialVersionUID = 3056811090283551797L;
    private String text;

}
