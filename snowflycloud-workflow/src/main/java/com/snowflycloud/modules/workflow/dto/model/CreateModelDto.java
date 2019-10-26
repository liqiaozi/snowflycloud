package com.snowflycloud.modules.workflow.dto.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CreateModelDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/24 16:56
 * @Version 1.0
 **/
@Data
public class CreateModelDto implements Serializable {

    private static final long serialVersionUID = -2155023543324766673L;

    /**
     * 模型分类.
     */
    private String category;

    /**
     * 模型名称.
     */
    private String name;

    /**
     * 流程key.
     */
    private String key;

    /**
     * 模型版本.
     */
    private Integer version;

    /**
     * 描述.
     */
    private String description;
}
