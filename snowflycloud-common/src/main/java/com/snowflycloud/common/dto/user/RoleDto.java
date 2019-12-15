package com.snowflycloud.common.dto.user;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Role
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 16:44
 * @Version 1.0
 **/
@Data
public class RoleDto extends SnowflyBaseEntity {
    private static final long serialVersionUID = 4531652716402068952L;

    private String roleId;

    private String roleCode;

    private String roleName;

    private String roleDesc;

}
