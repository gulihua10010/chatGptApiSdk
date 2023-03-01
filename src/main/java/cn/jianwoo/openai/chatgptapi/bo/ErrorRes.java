package cn.jianwoo.openai.chatgptapi.bo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 19:18
 */
@Data
@ToString
@Builder
public class ErrorRes implements Serializable
{
    private static final long serialVersionUID = 4949702843041546727L;
    private Error error;

    @Data
    @ToString
    public static class Error implements Serializable
    {
        private static final long serialVersionUID = -5581255954819882957L;
        private String message;
        private String type;
        private String param;
        private String code;
    }

}
