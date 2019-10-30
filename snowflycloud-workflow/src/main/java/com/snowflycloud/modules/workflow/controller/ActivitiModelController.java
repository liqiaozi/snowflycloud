package com.snowflycloud.modules.workflow.controller;

import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.model.CreateModelDto;
import com.snowflycloud.modules.workflow.dto.model.ModelSearchDto;
import com.snowflycloud.modules.workflow.service.SnowModelService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    @Autowired
    private SnowModelService snowModelService;


    /**
     * 新建流程模型，返回模型id
     *
     * @param createModelDto
     * @return
     */
    @PostMapping(value = "/create")
    public ResultResponse createModel(@RequestBody CreateModelDto createModelDto) throws IOException {
        String modelId = snowModelService.createModel(createModelDto);
        return ResultResponse.ok(modelId);
    }


    /**
     * 跳转模型编辑器页面
     *
     * @param modelId
     * @param response
     */
    @GetMapping(value = "/{modelId}")
    public void queryModelDetail(@PathVariable String modelId, HttpServletResponse response) throws IOException {
        snowModelService.queryModelDetail(modelId, response);
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
        PageResult<Model> result = snowModelService.getModelList(modelSearchDto, pageSize, pageNumber);
        return ResultResponse.ok(result);
    }


    /**
     * 刪除模型（支持批量）
     * @param modelIdList
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultResponse deleteByIds(@RequestBody List<String> modelIdList) {
        return snowModelService.deleteModel(modelIdList);
    }

    /**
     * 导出模型的xml文件
     * @param modelId
     * @param response
     */
    @GetMapping(value = "/export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response){
        snowModelService.export(modelId,response);
    }

    /**
     * 部署模型
     * @param modelId
     * @return
     */
    @PostMapping(value = "/deploy/{modelId}")
    public ResultResponse deploy(@PathVariable("modelId") String modelId){
        return snowModelService.deploy(modelId);
    }



}
