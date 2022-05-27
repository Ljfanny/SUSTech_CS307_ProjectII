package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.ContractMapper;
import com.database.projectii.mapper.OrderMapper;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private OrderMapper orderMapper;

    public Object selectContractCount(){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(distinct contract_number)");
        return orderMapper.selectObjs(queryWrapper);
    }

    public List<Map<String,Object>> selectAll() {
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        contractQueryWrapper.select("*");
        return contractMapper.selectMaps(contractQueryWrapper);
    }

    public List<Map<String, Object>> selectContractByAny(Contract contract) {
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        boolean isDate = false;
        boolean isOther = false;
        if (contract.getNumber() != null) {
            isOther = true;
            contractQueryWrapper.eq("number", contract.getNumber());
        }
        if (contract.getManager() != null) {
            isOther = true;
            contractQueryWrapper.eq("manager", contract.getManager());
        }
        if (contract.getDate() != null) {
            isDate = true;
        }
        if (contract.getEnterprise() != null) {
            isOther = true;
            contractQueryWrapper.eq("enterprise", contract.getEnterprise());
        }
        if (contract.getCenter() != null) {
            isOther = true;
            contractQueryWrapper.eq("center", contract.getCenter());
        }
        List<Map<String, Object>> contracts;
        if (isOther) {
            contracts = contractMapper.selectMaps(contractQueryWrapper);
        } else {
            contracts = selectAll();
        }
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> map : contracts) {
            boolean check = isDate &&
                contract.getDate().getTime() == ((Date) map.get("date")).getTime();
            if (check == isDate) {
                res.add(map);
            }
        }
        return res;
    }

    public boolean deleteContractByAny(Contract contract) {
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        boolean isDate = false;
        boolean isOther = false;
        if (contract.getNumber() != null) {
            isOther = true;
            contractQueryWrapper.eq("number", contract.getNumber());
        }
        if (contract.getManager() != null) {
            isOther = true;
            contractQueryWrapper.eq("manager", contract.getManager());
        }
        if (contract.getDate() != null) {
            isDate = true;
        }
        if (contract.getEnterprise() != null) {
            isOther = true;
            contractQueryWrapper.eq("enterprise", contract.getEnterprise());
        }
        if (contract.getCenter() != null) {
            isOther = true;
            contractQueryWrapper.eq("center", contract.getCenter());
        }
        List<Map<String, Object>> contracts;
        if (isOther) {
            contracts = contractMapper.selectMaps(contractQueryWrapper);
        } else {
            contracts = selectAll();
        }
        for (Map<String, Object> map : contracts) {
            boolean check = isDate &&
                contract.getDate().getTime() == ((Date) map.get("date")).getTime();
            if (check == isDate) {
                contractMapper.deleteByMap(map);
            }
        }
        return true;
    }
}
