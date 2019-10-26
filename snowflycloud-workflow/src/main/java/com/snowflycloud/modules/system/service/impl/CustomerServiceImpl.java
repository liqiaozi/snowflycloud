package com.snowflycloud.modules.system.service.impl;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.system.dao.BusinessCategoryDao;
import com.snowflycloud.modules.system.dao.ClientDao;
import com.snowflycloud.modules.system.dao.CustomerDao;
import com.snowflycloud.modules.system.domain.BusinessCategory;
import com.snowflycloud.modules.system.domain.Client;
import com.snowflycloud.modules.system.domain.Customer;
import com.snowflycloud.modules.system.domain.CustomerConfigDto;
import com.snowflycloud.modules.system.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName CustomerServiceImpl
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 17:14
 * @Version 1.0
 **/
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private BusinessCategoryDao businessCategoryDao;

    @Override
    public ResultResponse queryCustomerConfig(String customrId) {

        Optional<Customer> customerOptional = customerDao.findById(customrId);
        if (!customerOptional.isPresent()) {
            logger.error("[queryCustomerConfig] 用户不存在，customrId = {}", customrId);
            return ResultResponse.error("用户不存在");
        }

        Customer customer = customerOptional.get();
        String customerId = customer.getCustomerId();
        List<Client> clientList = clientDao.findByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(clientList)) {
            for (Client client : clientList) {
                String clentId = client.getClentId();
                List<BusinessCategory> categoryList = businessCategoryDao.findByClientId(clentId);
                client.setCategoryList(categoryList);
            }
        }

        CustomerConfigDto customerConfigDto = CustomerConfigDto.builder().customer(customer).clientList(clientList).build();
        return ResultResponse.ok(customerConfigDto);

    }
}
