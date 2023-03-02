package cn.jianwoo.openai.chatgptapi.bo;

import java.io.File;
import java.io.Serializable;

import cn.jianwoo.openai.chatgptapi.constants.Model;
import com.alibaba.fastjson2.annotation.JSONField;
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
public class AudioReq implements Serializable
{
    private static final long serialVersionUID = 3056811090283551797L;
    /** (Required) ID of the model to use. Only whisper-1 is currently available. */
    private String model;

    /** (Required) The audio file to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm. */
    private File file;

    /**
     * An optional text to guide the model's style or continue a previous audio segment. The prompt should match the
     * audio language.
     */
    private String prompt;
    /** The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt. */
    @JSONField(name = "response_format")
    private String responseFormat;
    /**
     * The sampling temperature, between 0 and 1. Higher values like 0.8 will make the output more random, while lower
     * values like 0.2 will make it more focused and deterministic. If set to 0, the model will use log probability to
     * automatically increase the temperature until certain thresholds are hit.
     */
    private String temperature;
    /**
     * The language of the input audio. Supplying the input language in ISO-639-1 format will improve accuracy and
     * latency.
     */
    private String language;

    public String getModel()
    {
        if (model == null)
        {
            return Model.WHISPER_1.getName();
        }
        return this.model;
    }
}
