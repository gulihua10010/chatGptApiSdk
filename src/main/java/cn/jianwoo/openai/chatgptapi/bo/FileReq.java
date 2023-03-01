package cn.jianwoo.openai.chatgptapi.bo;

import java.io.File;
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
public class FileReq implements Serializable
{
    private static final long serialVersionUID = 3056811090283551797L;
    /** (Required) The intended purpose of the uploaded documents. */
    private String purpose;

    /** (Required) Name of the JSON Lines file to be uploaded. */
    private File file;

}
