package com.zgli.drools.service;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.zgli.drools.config.DroolsConfiguration;
import com.zgli.drools.model.Customer;

@Service
public class CustomerService {

    private KieSession kieSession=new DroolsConfiguration().getKieSession();

    public Customer insertCustomer(Customer customer){
        kieSession.insert(customer);
        kieSession.fireAllRules();
        return  customer;

    }

}

