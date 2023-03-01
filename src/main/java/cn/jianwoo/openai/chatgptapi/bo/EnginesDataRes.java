package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class EnginesDataRes implements Serializable
{
    private static final long serialVersionUID = 1228999036969758493L;
    private String id;

    private String object;

    private String owner;

    private Boolean ready;
}
