package com.snowflycloud.email.modules.email.enums;

/**
 * @file: AcountElectionAlgorithmEnum
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-09-01 13:58
 * @version: v1.0.0
 */
public enum AcountElectionAlgorithmEnum {

    /**
     * 轮询
     */
    ROUNDROBIN,
    /**
     * 随机
     */
    RANDOM,
    /**
     * 一致性哈希
     */
    CONSISTENT_HASH

}
