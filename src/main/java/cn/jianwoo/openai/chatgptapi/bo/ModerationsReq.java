package cn.jianwoo.openai.chatgptapi.bo;

import java.io.File;
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
public class ModerationsReq implements Serializable
{
    private static final long serialVersionUID = 3620948657761062623L;
    /** (Required) The input text to classify. */
    private String input;

    /** Two content moderations models are available: text-moderation-stable and text-moderation-latest. */
    private String model;


}
