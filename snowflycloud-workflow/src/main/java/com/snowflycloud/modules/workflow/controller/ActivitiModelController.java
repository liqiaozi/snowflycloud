package com.snowflycloud.modules.workflow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.model.CreateModelDto;
import com.snowflycloud.modules.workflow.dto.model.ModelSearchDto;
import com.snowflycloud.modules.workflow.service.ModelService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName ActivitiModelController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/24 13:38
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/model/v1")
public class ActivitiModelController {
    private static final Logger logger = LoggerFactory.getLogger(ActivitiModelController.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 新建流程模型，返回模型id
     *
     * @param createModelDto
     * @return
     */
    @PostMapping(value = "/create")
    public ResultResponse createModel(@RequestBody CreateModelDto createModelDto) throws IOException {
        String modelId = modelService.createModel(createModelDto);
        return ResultResponse.ok(modelId);
    }

    /**
     * 新建流程模型
     *
     * @param modelId
     * @param response
     */
    @GetMapping(value = "/{modelId}")
    public void queryModelDetail(@PathVariable String modelId, HttpServletResponse response) throws IOException {

        modelService.queryModelDetail(modelId, response);
    }


    /**
     * 查询模型列表
     *
     * @param modelSearchDto
     * @return
     */
    @PostMapping(value = "/list")
    public ResultResponse modelList(@RequestBody ModelSearchDto modelSearchDto, @RequestParam(required = false, defaultValue = "1000") Integer pageSize,
                                    @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {

        PageResult<Model> result = modelService.getModelList(modelSearchDto, pageSize, pageNumber);
        return ResultResponse.ok(result);
    }

    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "/export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
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
            IOUtils.copy(in, response.getOutputStream());
//                String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            String filename = modelData.getName() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            response.flushBuffer();
        } catch (Exception e){
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


}
