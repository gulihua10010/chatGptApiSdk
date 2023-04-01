package cn.jianwoo.openai.chatgptapi.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gulihua
 * @Description
 * @date 2023-03-24 01:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpFailedBO {
    private String msg;
    private Throwable e;

    public HttpFailedBO(String msg) {
        this.msg = msg;
    }

    public HttpFailedBO(Throwable e) {
        this.e = e;
    }
}
