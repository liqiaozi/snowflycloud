package com.snowflycloud.modules.workflow.dto.instance;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName BusinessObj
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/31 14:53
 * @Version 1.0
 **/
@Data
public class BusinessObj implements Serializable {

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 申请人.
     */
    private String applicant;

    /**
     * 标题.
     */
    private String title;

    /**
     * 流程详情地址.
     */
    private String detailAddress;

    /**
     * businessKey
     */
    private String businessKey;

    /**
     * 业务变量
     */
    private Map<String, Object> businessVariables;
}
