package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.ContractMapper;
import com.database.projectii.mapper.OrderMapper;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Order;
import com.database.projectii.model.Staff;
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

    public long selectContractCount(){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(distinct contract_number) as cnt");
        long result = orderMapper.selectCount(queryWrapper);
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*) as cnt");
        result += contractMapper.selectCount(contractQueryWrapper);
        return result;
    }

    public List<Map<String,Object>> selectAll() {
        QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
        contractQueryWrapper.select("*");
        return contractMapper.selectMaps(contractQueryWrapper);
    }
}
