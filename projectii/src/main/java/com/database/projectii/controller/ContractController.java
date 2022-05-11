package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.service.impl.ContractServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @GetMapping("/getContractCount")
    public Data getContractCount(){
        long result = contractServiceImpl.selectContractCount();
        return new Data(result, Message.SUCCESS);
    }

}
