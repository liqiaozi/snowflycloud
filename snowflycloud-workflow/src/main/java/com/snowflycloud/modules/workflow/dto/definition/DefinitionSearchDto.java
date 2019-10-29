package com.snowflycloud.modules.workflow.dto.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DefinitionSearchDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/29 14:28
 * @Version 1.0
 **/
@Data
public class DefinitionSearchDto implements Serializable {
    private static final long serialVersionUID = -1036518484562897559L;

    private String tenantId;

    private String category;

    private String name;

    private String processKey;

    private Integer status;

}
