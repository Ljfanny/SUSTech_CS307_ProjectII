package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.CenterMapper;
import com.database.projectii.model.Center;
import com.database.projectii.model.Staff;
import com.database.projectii.service.CenterService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterServiceImpl implements CenterService {

    @Autowired
    private CenterMapper centerMapper;

    public Center selectCenterById(Integer id){
        return centerMapper.selectById(id);
    }

    public boolean updateCenter(Center center){
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.eq("id", center.getId());
        Center ctr = centerMapper.selectOne(centerQueryWrapper);
        if (ctr == null)
            return false;
        centerMapper.updateById(center);
        return true;
    }

    public boolean deleteCenter(Integer id){
        Center ctr = centerMapper.selectById(id);
        if (ctr == null)
            return false;
        centerMapper.deleteById(id);
        return true;
    }

    public boolean insertCenter(Center center){
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.eq("id", center.getId());
        Center ctr = centerMapper.selectOne(centerQueryWrapper);
        if (ctr != null)
            return false;
        centerMapper.insert(center);
        return true;
    }

    public List<Map<String,Object>> selectAll() {
        QueryWrapper<Center> centerQueryWrapper = new QueryWrapper<>();
        centerQueryWrapper.select("*");
        return centerMapper.selectMaps(centerQueryWrapper);
    }
}
