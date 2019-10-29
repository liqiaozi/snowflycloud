package com.snowflycloud.modules.workflow.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.definition.DefinitionSearchDto;
import com.snowflycloud.modules.workflow.service.SnowDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ActivitiProcessDefinitionController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/29 13:54
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/definition/v1")
public class ActivitiProcessDefinitionController {

    @Autowired
    private SnowDefinitionService snowDefinitionService;


    /**
     * 通过id删除流程定义
     * 编辑节点分配用户
     * 通过流程定义id获取流程节点
     * 通过key获取最新的流程
     */

    /**
     * 多条件分页查询流程定义列表
     *
     * @param definitionSearchDto
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @PostMapping(value = "/list")
    public ResultResponse list(@RequestBody DefinitionSearchDto definitionSearchDto, @RequestParam(required = false, defaultValue = "1000") Integer pageSize,
                               @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return snowDefinitionService.pageList(definitionSearchDto, pageSize, pageNumber);
    }

    /**
     * 导入部署
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/import/deploy")
    public ResultResponse deploy(@RequestParam(value = "file") MultipartFile file) {
        return snowDefinitionService.importDeploy(file);
    }


    /**
     * 显示流程图，不带流程跟踪,
     *
     * @param processDefinitionId
     * @param resourceType        png | image 新开窗口展示流程   xml则下载文件
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/showResource")
    public void showResource(@RequestParam String processDefinitionId, @RequestParam String resourceType, HttpServletResponse response) throws IOException {
        snowDefinitionService.showResourceNoTrace(processDefinitionId, resourceType, response);
    }

    /**
     * 激活/挂起流程定义.
     *
     * @param processDefinitionId
     * @param status
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResultResponse updateStatusByProcessDefinitionId(@RequestParam String processDefinitionId, @RequestParam String status) {
        return snowDefinitionService.updateStatusByProcessDefinitionId(processDefinitionId, status);
    }

    /**
     * 流程定义转换为模型.
     *
     * @param processDefinitionId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/convertToModel/{processDefinitionId}")
    public ResultResponse convertToModel(@PathVariable String processDefinitionId) throws Exception {
        return snowDefinitionService.convertToModel(processDefinitionId);
    }




}
