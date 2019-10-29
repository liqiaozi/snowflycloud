package com.snowflycloud.modules.workflow.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.definition.DefinitionSearchDto;
import com.snowflycloud.modules.workflow.dto.definition.SnowProcessDefinitionDto;
import com.snowflycloud.modules.workflow.service.SnowDefinitionService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @ClassName SnowDefinitionServiceImpl
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/29 14:45
 * @Version 1.0
 **/
@Service
public class SnowDefinitionServiceImpl implements SnowDefinitionService {

    private static final Logger logger = LoggerFactory.getLogger(SnowDefinitionServiceImpl.class);

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public ResultResponse pageList(DefinitionSearchDto definitionSearchDto, Integer pageSize, Integer pageNumber) {

        String tenantId = definitionSearchDto.getTenantId();
        String category = definitionSearchDto.getCategory();
        String name = definitionSearchDto.getName();
        String processKey = definitionSearchDto.getProcessKey();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(tenantId)) {
            processDefinitionQuery.processDefinitionTenantId(tenantId);
        }
        if (StringUtils.isNotBlank(category)) {
            processDefinitionQuery.processDefinitionCategory(category);
        }
        if (StringUtils.isNotBlank(name)) {
            processDefinitionQuery.processDefinitionNameLike(name);
        }
        if (StringUtils.isNotBlank(processKey)) {
            processDefinitionQuery.processDefinitionKeyLike(processKey);
        }
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageSize * (pageNumber - 1), pageSize);
        long count = processDefinitionQuery.count();

        PageResult<SnowProcessDefinitionDto> pageResult = new PageResult(pageNumber, pageSize, count);

        List<SnowProcessDefinitionDto> list = Lists.newArrayList();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            SnowProcessDefinitionDto snowProcessDefinitionDto = new SnowProcessDefinitionDto();
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

            snowProcessDefinitionDto.setId(processDefinition.getId());
            snowProcessDefinitionDto.setName(processDefinition.getName());
            snowProcessDefinitionDto.setKey(processDefinition.getKey());
            snowProcessDefinitionDto.setDeploymentId(processDefinition.getDeploymentId());
            snowProcessDefinitionDto.setVersion(processDefinition.getVersion());
            snowProcessDefinitionDto.setResourceName(processDefinition.getResourceName());
            snowProcessDefinitionDto.setDiagramResourceName(processDefinition.getDiagramResourceName());
            snowProcessDefinitionDto.setDeploymentTime(deployment.getDeploymentTime());
            snowProcessDefinitionDto.setSuspended(processDefinition.isSuspended());
            snowProcessDefinitionDto.setTenantId(processDefinition.getTenantId());
            snowProcessDefinitionDto.setCategory(processDefinition.getCategory());
            list.add(snowProcessDefinitionDto);

        }
        pageResult.setList(list);
        return ResultResponse.ok(pageResult);
    }

    @Override
    public ResultResponse importDeploy(MultipartFile file) {
        String filename = file.getOriginalFilename();
        logger.info("[importDeploy] filename = {}", filename);
        try {
            InputStream inputStream = file.getInputStream();
            Deployment deployment = null;

            String extension = FilenameUtils.getExtension(filename);
            if ("zip".equals(extension) || "bar".equals(extension)) {
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(filename, inputStream).deploy();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[importDeploy] 导入部署发生异常，异常信息为： {}", e.getMessage());
            return ResultResponse.error("导入部署发生异常");
        }

        return ResultResponse.ok();
    }

    @Override
    public void showResourceNoTrace(String processDefinitionId, String resourceType, HttpServletResponse response) throws IOException {
        logger.info("[showResourceNoTrace] 查看流程资源信息，processDefinitionId = {}，resourceType = {}", processDefinitionId, resourceType);

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("png") || resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
            InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } else {
            resourceName = processDefinition.getResourceName();
            InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
            byte[] b = toByteArray(resourceAsStream);

            ByteArrayInputStream in = new ByteArrayInputStream(b);
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(resourceName, "UTF-8"));
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        }

    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    @Override
    public ResultResponse updateStatusByProcessDefinitionId(String processDefinitionId, String status) {
        logger.info("[updateStatusByProcessDefinitionId] 激活/挂起流程定义，processDefinitionId = {}，status = {}");

        if ("active".equalsIgnoreCase(status)) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else if ("suspend".equalsIgnoreCase(status)) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
        }
        return ResultResponse.ok();
    }

    @Override
    public ResultResponse convertToModel(String processDefinitionId) throws Exception {
        logger.info("[convertToModel] 流程定义转换为模型, processDefinitionId = {}", processDefinitionId);

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, "UTF-8");

        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStreamReader);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);

        BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
        ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

        Model newModel = repositoryService.newModel();
        newModel.setTenantId(processDefinition.getTenantId());
        newModel.setCategory(processDefinition.getCategory());
        newModel.setName(processDefinition.getName());
        newModel.setKey(processDefinition.getKey());

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        objectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        objectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        newModel.setMetaInfo(objectNode.toString());

        repositoryService.saveModel(newModel);
        repositoryService.addModelEditorSource(newModel.getId(), modelNode.toString().getBytes("utf-8"));
        return ResultResponse.ok("转换成功，请到【模型列表】进行查看!");

    }


}
