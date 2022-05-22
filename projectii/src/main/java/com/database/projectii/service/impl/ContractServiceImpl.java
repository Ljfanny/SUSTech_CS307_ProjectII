package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.ContractMapper;
import com.database.projectii.mapper.OrderMapper;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Inventory;
import com.database.projectii.model.Order;
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
        if (contract.getNumber() != null) {
            contractQueryWrapper.eq("number", contract.getNumber());
        }
        if (contract.getManager() != null) {
            contractQueryWrapper.eq("manager", contract.getManager());
        }
        if (contract.getDate() != null) {
            contractQueryWrapper.eq("date", contract.getDate());
        }
        if (contract.getEnterprise() != null) {
            contractQueryWrapper.eq("enterprise", contract.getEnterprise());
        }
        if (contract.getCenter() != null) {
            contractQueryWrapper.eq("center", contract.getCenter());
        }
        return contractMapper.selectMaps(contractQueryWrapper);
    }

    public boolean deleteContractByAny(Contract contract) {
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        if (contract.getNumber() != null) {
            contractQueryWrapper.eq("number", contract.getNumber());
        }
        if (contract.getManager() != null) {
            contractQueryWrapper.eq("manager", contract.getManager());
        }
        if (contract.getDate() != null) {
            contractQueryWrapper.eq("date", contract.getDate());
        }
        if (contract.getEnterprise() != null) {
            contractQueryWrapper.eq("enterprise", contract.getEnterprise());
        }
        if (contract.getCenter() != null) {
            contractQueryWrapper.eq("center", contract.getCenter());
        }
        List<Map<String, Object>> contracts = contractMapper.selectMaps(contractQueryWrapper);
        for (Map<String, Object> map : contracts) {
            contractMapper.deleteById((int) map.get("id"));
        }
        return true;
    }
}
