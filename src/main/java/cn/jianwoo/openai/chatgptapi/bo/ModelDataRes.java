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
 * @date 2023-02-28 18:25
 */
@Data
@ToString
@Builder
public class ModelDataRes implements Serializable {
    private static final long serialVersionUID = -7315472038774372903L;
    private String id;

    private String object;

    private int created;

    @JSONField(name = "owned_by")
    private String ownedBy;

    private List<Permission> permission;

    private String root;

    private String parent;


    @lombok.Data
    @ToString
    @Builder
    public static class Permission implements Serializable {
        private static final long serialVersionUID = 5829901289792852545L;
        private String id;

        private String object;

        private Integer created;

        @JSONField(name = "allow_create_engine")
        private Boolean allowCreateEngine;

        @JSONField(name = "allow_sampling")
        private Boolean allowSampling;

        @JSONField(name = "allow_logprobs")
        private Boolean allowLogprobs;

        @JSONField(name = "allow_search_indices")
        private Boolean allowSearchIndices;

        @JSONField(name = "allow_view")
        private Boolean allowView;

        @JSONField(name = "allow_fine_tuning")
        private Boolean allowFineTuning;

        private String organization;

        private String group;

        @JSONField(name = "is_blocking")
        private Boolean isBlocking;
    }

}
