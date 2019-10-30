package com.snowflycloud.modules.workflow.service;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.definition.DefinitionSearchDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @file: SnowDefinitionService
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-10-29 14:44
 * @version: v1.0.0
 */
public interface SnowDefinitionService {
    /**
     * 多条件分页查询流程定义列表
     *
     * @param definitionSearchDto
     * @param pageSize
     * @param pageNumber
     * @return
     */
    ResultResponse pageList(DefinitionSearchDto definitionSearchDto, Integer pageSize, Integer pageNumber);

    /**
     * 导入部署.
     *
     * @param file
     * @return
     */
    ResultResponse importDeploy(MultipartFile file);


    /**
     * 显示流程资源信息
     *
     * @param processDefinitionId
     * @param resourceType
     * @param response
     */
    void showResourceNoTrace(String processDefinitionId, String resourceType, HttpServletResponse response) throws IOException;

    /**
     * 激活、挂起流程定义/
     *
     * @param processDefinitionId
     * @param status
     * @return
     */
    ResultResponse updateStatusByProcessDefinitionId(String processDefinitionId, String status);

    /**
     * 流程定义转换为模型.
     *
     * @param processDefinitionId
     * @return
     */
    ResultResponse convertToModel(String processDefinitionId) throws Exception;

    /**
     * 删除流程定义.
     *
     * @param deployId
     * @param force
     * @return
     */
    ResultResponse deleteProcessDefinition(String deployId, Boolean force);
}
