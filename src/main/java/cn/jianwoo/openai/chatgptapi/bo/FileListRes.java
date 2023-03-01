package cn.jianwoo.openai.chatgptapi.bo;

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
public class FileListRes implements Serializable
{
    private static final long serialVersionUID = 8143210348945550075L;
    private List<FileDetRes> data;

    private String object;

}
