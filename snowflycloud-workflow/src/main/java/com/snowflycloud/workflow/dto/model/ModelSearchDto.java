package com.snowflycloud.workflow.dto.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ModelSearchDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/24 21:23
 * @Version 1.0
 **/
@Data
public class ModelSearchDto implements Serializable {
    private static final long serialVersionUID = 5769772727121093467L;

    private String modelId;

    private String modelName;

    private String key;

    private String tenantId;


}
