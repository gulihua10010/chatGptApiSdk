package cn.jianwoo.openai.chatgptapi.bo;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.io.Serializable;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-28 18:57
 */
@Data
@ToString
@Builder
public class ImageReq implements Serializable
{

    private static final long serialVersionUID = 2092072774983708273L;
    /**
     * (Required) A text description of the desired image(s). The maximum length is 1000 characters.
     * 
     */
    private String prompt;

    /** The number of images to generate. Must be between 1 and 10. */
    private Integer n;

    /** The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024. */
    private Integer size;

    /** The format in which the generated images are returned. Must be one of url or b64_json. */
    @JSONField(name = "response_format")
    private String responseFormat;
    /** A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. */
    private String user;

    /**
     * (Required in Edit) The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided, image must have
     * transparency, which will be used as the mask.
     *
     */
    private File image;
    /**
     *
     * An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be
     * edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     *
     */
    private File mask;

}
