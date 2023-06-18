package cn.jianwoo.openai.chatgptapi.bo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 方法实体类
 * 
 * <pre>
 "functions": [
 *         {
 *             "name": "getCurrentWeather",
 *             "description": "查询天气",
 *             "parameters": {
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
 *         }
 *     ]
 * </pre>
 */
@Data
@Builder
public class FunctionsReq implements Serializable
{
    /**
     * 方法名称
     */
    private String name;
    /**
     * 方法描述
     */
    private String description;
    /**
     * 方法参数, 如需扩展可继承{@link ParametersReq}
     */
    private ParametersReq parameters;
}
