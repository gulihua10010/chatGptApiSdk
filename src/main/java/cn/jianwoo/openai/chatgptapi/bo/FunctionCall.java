package cn.jianwoo.openai.chatgptapi.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 函数调用返回值<br>
 * 
 * <pre>
 *     "function_call": {
 *                     "name": "getCurrentWeather",
 *                     "arguments": "{\n\"location\": \"南京\",\n\"format\": \"摄氏度\"\n}"
 *                 }
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionCall
{
    /**
     * 方法名
     */
    private String name;
    /**
     * 方法参数
     */
    private String arguments;
}
