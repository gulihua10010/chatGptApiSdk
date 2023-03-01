package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 23:42
 */
@Data
@ToString
@Builder
public class EventListRes implements Serializable
{
    private static final long serialVersionUID = 6898615781807162470L;
    private String object;

    private List<ErrorRes> data;

}
