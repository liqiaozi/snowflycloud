package com.snowflycloud.workflow.service;

import com.github.pagehelper.PageInfo;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.workflow.dto.model.CreateModelDto;
import com.snowflycloud.workflow.dto.model.ModelSearchDto;
import org.activiti.engine.repository.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @file: ModelService
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-10-24 19:57
 * @version: v1.0.0
 */
public interface ModelService {

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
    PageInfo<Model> getModelList(ModelSearchDto modelSearchDto, Integer pageSize, Integer pageNumber);

}
