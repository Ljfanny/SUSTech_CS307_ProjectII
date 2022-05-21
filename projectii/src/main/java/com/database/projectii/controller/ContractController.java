package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Contract;
import com.database.projectii.service.impl.ContractServiceImpl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@CrossOrigin
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @GetMapping("/all")
    public Data getAllContracts() {
        ArrayList<Contract> contractArrayList = new ArrayList<>();
        List<Map<String, Object>> res = contractServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            contractArrayList.add(new Contract((String) map.get("contract_number"),
                (String) map.get("contract_manager"),
                (Date) map.get("contract_date"), (String) map.get("enterprise"),
                (String) map.get("center")));
        }
        return new Data(contractArrayList, Message.SUCCESS);
    }

    @GetMapping("/getContractCount")
    public Data getContractCount() {
        Object result = contractServiceImpl.selectContractCount();
        return new Data(result, Message.SUCCESS);
    }
}
