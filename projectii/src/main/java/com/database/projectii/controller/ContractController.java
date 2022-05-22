package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Center;
import com.database.projectii.model.Contract;
import com.database.projectii.service.impl.ContractServiceImpl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public Data getByAny(
        @RequestParam(value = "number", required = false, defaultValue = "") String number,
        @RequestParam(value = "manager", required = false, defaultValue = "") String manager,
        @RequestParam(value = "date", required = false, defaultValue = "") Date date,
        @RequestParam(value = "enterprise", required = false, defaultValue = "") String enterprise,
        @RequestParam(value = "center", required = false, defaultValue = "") String center) {
        Contract contract =
            new Contract(number.equals("") ? null : number, manager.equals("") ? null : manager,
                date, enterprise.equals("") ? null : enterprise, center.equals("") ? null : center);
        List<Map<String, Object>> mapList = contractServiceImpl.selectContractByAny(contract);
        ArrayList<Contract> contracts = new ArrayList<>();
        if (mapList.isEmpty()) {
            return new Data(null, Message.SUCCESS);
        } else {
            for (Map<String, Object> map : mapList) {
                contracts.add(new Contract((String) map.get("contract_number"),
                    (String) map.get("contract_manager"),
                    (Date) map.get("contract_date"), (String) map.get("enterprise"),
                    (String) map.get("center")));
            }
        }
        return new Data(contracts, Message.SUCCESS);
    }

    @DeleteMapping
    public Data DeleteByAny(
        @RequestParam(value = "number", required = false, defaultValue = "") String number,
        @RequestParam(value = "manager", required = false, defaultValue = "") String manager,
        @RequestParam(value = "date", required = false, defaultValue = "") Date date,
        @RequestParam(value = "enterprise", required = false, defaultValue = "") String enterprise,
        @RequestParam(value = "center", required = false, defaultValue = "") String center) {
        Contract contract =
            new Contract(number.equals("") ? null : number, manager.equals("") ? null : manager,
                date, enterprise.equals("") ? null : enterprise, center.equals("") ? null : center);
        boolean result = contractServiceImpl.deleteContractByAny(contract);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }
}
