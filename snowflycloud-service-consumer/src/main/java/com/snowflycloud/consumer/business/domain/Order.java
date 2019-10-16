package com.snowflycloud.consumer.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Order
 * @Description TODO
 * @Author 25823
 * @Date 2019/10/13 16:09
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -8209340494268670241L;

    private String name;

    private String orderId;

    private String orderName;
}
