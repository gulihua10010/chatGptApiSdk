package cn.jianwoo.openai.chatgptapi.bo;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 函数参数里的属性类, 如需扩展可继承{@link PropertyReq}<br>
 * 
 * <pre>
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
 *                 }
 * </pre>
 * 
 * @author gulihua
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyReq
{
    private String name;
    private String type;
    private String description;
    private List<String> enums;

    public JSONObject toJSON()
    {
        JSONObject o = (JSONObject) JSON.toJSON(this);
        o.remove("name");
        o.remove("enums");
        o.put("enum", enums);
        JSONObject prop = new JSONObject();
        prop.put(name, o);
        return prop;

    }

}
