package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.CenterMapper;
import com.database.projectii.model.Center;
import com.database.projectii.model.Staff;
import com.database.projectii.service.CenterService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterServiceImpl implements CenterService {

    @Autowired
    private CenterMapper centerMapper;

    public boolean updateCenter(Center center) {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.eq("id", center.getId());
        Center ctr = centerMapper.selectOne(centerQueryWrapper);
        if (ctr == null) {
            return false;
        }
        centerMapper.updateById(center);
        return true;
    }

    public List<Map<String, Object>> selectCenterByAny(Center center) {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        if (center.getId() != null) {
            centerQueryWrapper.eq("id", center.getId());
        }
        if (center.getName() != null) {
            centerQueryWrapper.eq("name", center.getName());
        }
        return centerMapper.selectMaps(centerQueryWrapper);
    }

    public boolean deleteCenterByAny(Center center) {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        if (center.getId() != null) {
            centerQueryWrapper.eq("id", center.getId());
        }
        if (center.getName() != null) {
            centerQueryWrapper.eq("name", center.getName());
        }
        List<Map<String, Object>> centers = centerMapper.selectMaps(centerQueryWrapper);
        for (Map<String, Object> map : centers) {
            centerMapper.deleteById((int) map.get("id"));
        }
        return true;
    }

    public boolean insertCenter(Center center) {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.eq("id", center.getId());
        Center ctr = centerMapper.selectOne(centerQueryWrapper);
        if (ctr != null) {
            return false;
        }
        centerMapper.insert(center);
        return true;
    }

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.select("*");
        return centerMapper.selectMaps(centerQueryWrapper);
    }
}
