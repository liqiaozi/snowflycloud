package com.snowflycloud.modules.system.dao;

import com.snowflycloud.modules.system.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @file: CustomerDao
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-10-26 16:45
 * @version: v1.0.0
 */
@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {

}
