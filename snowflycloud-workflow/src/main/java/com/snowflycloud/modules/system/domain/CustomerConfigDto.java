package com.snowflycloud.modules.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CustomerConfigDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 16:57
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerConfigDto implements Serializable {
    private static final long serialVersionUID = 1845854691183197326L;

    private Customer customer;

    private List<Client> clientList;
}
