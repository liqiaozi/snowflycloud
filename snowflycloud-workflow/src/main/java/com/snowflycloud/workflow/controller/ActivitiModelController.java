package com.snowflycloud.workflow.controller;

import com.github.pagehelper.PageInfo;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.workflow.dto.model.CreateModelDto;
import com.snowflycloud.workflow.dto.model.ModelSearchDto;
import com.snowflycloud.workflow.service.ModelService;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        PageInfo<Model> result = modelService.getModelList(modelSearchDto, pageSize, pageNumber);
        return ResultResponse.ok(result);
    }


}
