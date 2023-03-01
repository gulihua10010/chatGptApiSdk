package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class EnginesListRes implements Serializable
{
    private static final long serialVersionUID = 5696979122264771764L;
    private List<EnginesDataRes> data;

    private String object;

}
