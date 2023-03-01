package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;
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
public class ImageRes implements Serializable
{

    private static final long serialVersionUID = -4375773431242699973L;
    private Integer created;

    private List<Data> data;

    @lombok.Data
    @ToString
    @Builder
    public static class Data implements Serializable
    {
        private static final long serialVersionUID = -8812438979978660848L;
        private String url;
        @JSONField(name = "b64_json")
        private String b64Json;
    }

}
