package cn.jianwoo.openai.chatgptapi.bo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
public class ModelRes implements Serializable
{
    private static final long serialVersionUID = 9172905867883603019L;
    private String object;

    private List<ModelDataRes> data;
}
