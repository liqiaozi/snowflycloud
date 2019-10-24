package com.snowflycloud.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.workflow.dto.model.CreateModelDto;
import com.snowflycloud.workflow.dto.model.ModelSearchDto;
import com.snowflycloud.workflow.service.ModelService;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ModelServiceImpl
 * @Description 流程模型服务
 * @Author lixuefei
 * @Date 2019/10/24 19:58
 * @Version 1.0
 **/
@Service
public class ModelServiceImpl implements ModelService {
    private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String createModel(CreateModelDto createModelDto) throws IOException {
        logger.info("[createModel] 创建流程模型. params = {}", JSONObject.toJSONString(createModelDto));

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(ModelDataJsonConstants.MODEL_NAME, createModelDto.getName());
        objectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        objectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, createModelDto.getDescription());

        Model model = repositoryService.newModel();
        model.setCategory(createModelDto.getCategory());
        model.setName(createModelDto.getName());
        model.setKey(createModelDto.getKey());
        model.setVersion(createModelDto.getVersion());
        model.setMetaInfo(objectNode.toString());
        repositoryService.saveModel(model);

        String modelId = model.getId();

        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "snowflying");
        editorNode.put("resourceId", "snowflying");

        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes("utf-8"));
        return modelId;
    }

    @Override
    public void queryModelDetail(String modelId, HttpServletResponse response) throws IOException {
        logger.info("[queryModelDetail] 跳转模型界面. modelId = {}", modelId);
        response.sendRedirect("/modeler.html?modelId=" + modelId);
    }

    @Override
    public PageInfo<Model> getModelList(ModelSearchDto modelSearchDto, Integer pageSize, Integer pageNumber) {

        String tenantId = modelSearchDto.getTenantId();
        String modelName = modelSearchDto.getModelName();
        String key = modelSearchDto.getKey();
        String modelId = modelSearchDto.getModelId();


        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotBlank(modelId)) {
            modelQuery.modelId(modelId);
        }
        if (StringUtils.isNotBlank(key)) {
            modelQuery.modelKey(key);
        }
        if (StringUtils.isNotBlank(modelName)) {
            modelQuery.modelNameLike(modelName);
        }
        if (StringUtils.isNotBlank(tenantId)) {
            modelQuery.modelTenantId(tenantId);
        }
        List<Model> list = modelQuery.listPage(pageSize * (pageNumber - 1), pageSize);

        long count = modelQuery.count();
        PageInfo<Model> pageInfo = new PageInfo<Model>(list);
        pageInfo.setTotal(count);

        return pageInfo;
    }


}
