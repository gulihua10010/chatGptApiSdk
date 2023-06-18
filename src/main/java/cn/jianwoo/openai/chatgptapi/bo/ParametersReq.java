package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 函数参数类, 如需扩展可继承{@link ParametersReq}<br>
 * 
 * <pre>
 * "parameters": {
 *                 "type": "object",
 *                 "properties": {
 *                     "location": {
 *                         "type": "string",
 *                         "description": "城市，比如：上海"
 *                     },
 *                     "format": {
 *                         "type": "string",
 *                         "description": "温度单位",
 *                         "enum": [
 *                             "摄氏度",
 *                             "华氏度"
 *                         ]
 *                     }
 *                 },
 *                 "required": [
 *                     "location"
 *                 ]
 *             }
 * </pre>
 * 
 * @author gulihua
 * @date 2023-06-17 23:42
 */
@Data
@Builder
public class ParametersReq implements Serializable
{
    /**
     * 参数类型
     */
    @Builder.Default
    private String type = "object";
    /**
     * 参数属性、描述
     */
    private Object properties;
    /**
     * 方法必输字段
     */
    private List<String> required;
}
