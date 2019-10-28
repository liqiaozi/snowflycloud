package com.snowflycloud.modules.workflow.service;

import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.model.CreateModelDto;
import com.snowflycloud.modules.workflow.dto.model.ModelSearchDto;
import org.activiti.engine.repository.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @file: SnowModelService
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-10-24 19:57
 * @version: v1.0.0
 */
public interface SnowModelService {

    /**
     * 创建模型
     *
     * @param createModelDto
     */
    String createModel(CreateModelDto createModelDto) throws IOException;


    /**
     * 跳转模型界面
     *
     * @param modelId
     * @param response
     * @return
     * @throws IOException
     */
    void queryModelDetail(String modelId, HttpServletResponse response) throws IOException;


    /**
     * 查询模型列表.
     *
     * @param modelSearchDto
     * @param pageSize
     * @param pageNumber
     * @return
     */
    PageResult<Model> getModelList(ModelSearchDto modelSearchDto, Integer pageSize, Integer pageNumber);

    /**
     * 批量删除模型
     *
     * @param modelIdList
     * @return
     */
    ResultResponse deleteModel(List<String> modelIdList);

    /**
     * 导出模型文件为xml
     *
     * @param modelId
     * @param response
     */
    void export(String modelId, HttpServletResponse response);

    /**
     * 部署模型
     * @param modelId
     * @return
     */
    ResultResponse deploy(String modelId);

}
