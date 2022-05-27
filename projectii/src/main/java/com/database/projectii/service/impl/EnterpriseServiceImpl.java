package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.EnterpriseMapper;
import com.database.projectii.model.Enterprise;
import com.database.projectii.service.EnterpriseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

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

    public List<Map<String, Object>> selectEnterpriseByAny(Enterprise enterprise) {
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        if (enterprise.getId() != null) {
            enterpriseQueryWrapper.eq("id", enterprise.getId());
        }
        if (enterprise.getName() != null) {
            enterpriseQueryWrapper.eq("name", enterprise.getName());
        }
        if (enterprise.getCity() != null) {
            enterpriseQueryWrapper.eq("city", enterprise.getCity());
        }
        if (enterprise.getCountry() != null) {
            enterpriseQueryWrapper.eq("country", enterprise.getCountry());
        }
        if (enterprise.getSupplyCenter() != null) {
            enterpriseQueryWrapper.eq("supply_center", enterprise.getSupplyCenter());
        }
        if (enterprise.getIndustry() != null) {
            enterpriseQueryWrapper.eq("industry", enterprise.getIndustry());
        }
        return enterpriseMapper.selectMaps(enterpriseQueryWrapper);
    }

    public boolean deleteEnterpriseByAny(Enterprise enterprise) {
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        if (enterprise.getId() != null) {
            enterpriseQueryWrapper.eq("id", enterprise.getId());
        }
        if (enterprise.getName() != null) {
            enterpriseQueryWrapper.eq("name", enterprise.getName());
        }
        if (enterprise.getCountry() != null) {
            enterpriseQueryWrapper.eq("country", enterprise.getCountry());
        }
        if (enterprise.getSupplyCenter() != null) {
            enterpriseQueryWrapper.eq("supply_center", enterprise.getSupplyCenter());
        }
        if (enterprise.getIndustry() != null) {
            enterpriseQueryWrapper.eq("industry", enterprise.getIndustry());
        }
        List<Map<String, Object>> centers = enterpriseMapper.selectMaps(enterpriseQueryWrapper);
        for (Map<String, Object> map : centers) {
            enterpriseMapper.deleteById((int) map.get("id"));
        }
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
