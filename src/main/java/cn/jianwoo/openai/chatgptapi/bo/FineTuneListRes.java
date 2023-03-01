package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;

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
public class FineTuneListRes implements Serializable
{
    private static final long serialVersionUID = 5059829239538390140L;
    private List<FineTunesRes> data;

    private String object;

}
