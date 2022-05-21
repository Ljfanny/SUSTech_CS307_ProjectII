package com.database.projectii.controller;


import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Center;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Enterprise;
import com.database.projectii.service.impl.EnterpriseServiceImpl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private Environment environment;

    @Autowired
    private EnterpriseServiceImpl enterpriseServiceImpl;

    @Autowired
    private Enterprise enterprise;

    @GetMapping("/{id}")
    public Data getById(@PathVariable Integer id) {
        Enterprise enterprise = enterpriseServiceImpl.selectEnterpriseById(id);
        if (enterprise == null) {
            return new Data(null, Message.NOT_SUCCESS);
        }
        return new Data(enterprise, Message.SUCCESS);
    }

    @GetMapping("/all")
    public Data getAllEnterprise() {
        ArrayList<Enterprise> enterpriseArrayList = new ArrayList<>();
        List<Map<String, Object>> res = enterpriseServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            enterpriseArrayList.add(new Enterprise((Integer) map.get("id"),
                (String) map.get("name"), (String) map.get("country"), (String) map.get("city"),
                (String) map.get("supply_center"), (String) map.get("industry")));
        }
        return new Data(enterpriseArrayList, Message.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public Data DeleteById(@PathVariable Integer id) {
        boolean result = enterpriseServiceImpl.deleteEnterprise(id);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PostMapping
    public Data insert(@RequestBody Enterprise enterprise) {
        boolean result = enterpriseServiceImpl.insertEnterprise(enterprise);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PutMapping
    public Data update(@RequestBody Enterprise enterprise) {
        boolean result = enterpriseServiceImpl.updateEnterprise(enterprise);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }
}
