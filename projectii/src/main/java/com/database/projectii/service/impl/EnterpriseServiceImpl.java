package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.EnterpriseMapper;
import com.database.projectii.model.Center;
import com.database.projectii.model.Enterprise;
import com.database.projectii.model.Staff;
import com.database.projectii.service.EnterpriseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    public Enterprise selectEnterpriseById(Integer id) {
        return enterpriseMapper.selectById(id);
    }

    public boolean updateEnterprise(Enterprise enterprise) {
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("id", enterprise.getId());
        Enterprise etr = enterpriseMapper.selectOne(enterpriseQueryWrapper);
        if (etr == null) {
            return false;
        }
        enterpriseMapper.updateById(enterprise);
        return true;
    }

    public boolean deleteEnterprise(Integer id) {
        Enterprise etr = enterpriseMapper.selectById(id);
        if (etr == null) {
            return false;
        }
        enterpriseMapper.deleteById(id);
        return true;
    }

    public boolean insertEnterprise(Enterprise enterprise) {
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("id", enterprise.getId());
        Enterprise etr = enterpriseMapper.selectOne(enterpriseQueryWrapper);
        if (etr != null) {
            return false;
        }
        enterpriseMapper.insert(enterprise);
        return true;
    }

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.select("*");
        return enterpriseMapper.selectMaps(enterpriseQueryWrapper);
    }
}
