package com.snowflycloud.modules.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.model.CreateModelDto;
import com.snowflycloud.modules.workflow.dto.model.ModelSearchDto;
import com.snowflycloud.modules.workflow.service.SnowModelService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ClassName ModelServiceImpl
 * @Description 流程模型服务
 * @Author lixuefei
 * @Date 2019/10/24 19:58
 * @Version 1.0
 **/
@Service
public class SnowModelServiceImpl implements SnowModelService {
    private static final Logger logger = LoggerFactory.getLogger(SnowModelServiceImpl.class);


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
    public PageResult<Model> getModelList(ModelSearchDto modelSearchDto, Integer pageSize, Integer pageNumber) {
        logger.info("[getModelList] 查询模型列表，params = {}, pageSize = {}, pageNumber = {}", JSONObject.toJSONString(modelSearchDto), pageSize, pageNumber);
        String tenantId = modelSearchDto.getTenantId();
        String modelName = modelSearchDto.getModelName();
        String key = modelSearchDto.getKey();
        String modelId = modelSearchDto.getModelId();

        ModelQuery modelQuery = repositoryService.createModelQuery().orderByCreateTime().desc();


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
        PageResult<Model> pageResult = new PageResult(pageNumber, pageSize, count);
        pageResult.setList(list);


        return pageResult;
    }

    @Override
    public ResultResponse deleteModel(List<String> modelIdList) {
        logger.info("[deleteModel] 刪除模型,params = {}", JSONObject.toJSONString(modelIdList));
        if (!CollectionUtils.isEmpty(modelIdList)) {
            modelIdList.forEach(modelId -> {
                repositoryService.deleteModel(modelId);
            });

        }

        return ResultResponse.ok();
    }

    @Override
    public void export(String modelId, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            //获取节点信息
            byte[] arg0 = repositoryService.getModelEditorSource(modelData.getId());
            JsonNode editorNode = new ObjectMapper().readTree(arg0);
            //将节点信息转换为xml
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            String filename = modelData.getName() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            out.write("未找到对应数据");
            e.printStackTrace();
        }

    }

    @Override
    public ResultResponse deploy(String modelId) {
        logger.info("[deploy] params = {}", modelId);

        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";

            Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                .tenantId(modelData.getTenantId())
                .category(modelData.getCategory())
                .addString(processName, new String(bpmnBytes, "utf-8"))
                .deploy();
            logger.info("[deploy] deployment = {}", JSONObject.toJSONString(deployment));
            return ResultResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[deploy] 部署失败，异常信息为： {}", e.getMessage());
            return ResultResponse.error("部署失败");

        }
    }


}
