package com.snowflycloud.provider.business.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author 25823
 * @Date 2019/10/13 16:03
 * @Version 1.0
 **/
@Data
@ApiModel(value = "用户实体类")
public class User implements Serializable {
    private static final long serialVersionUID = -4450196934211870266L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private String age;

}
