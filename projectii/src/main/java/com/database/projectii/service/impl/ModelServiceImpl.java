package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.ModelMapper;
import com.database.projectii.model.Enterprise;
import com.database.projectii.model.Model;
import com.database.projectii.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

    public Model selectModelById(Integer id) {
        return modelMapper.selectById(id);
    }

    public boolean updateModel(Model model) {
        QueryWrapper<Model> modelQueryWrapper = new QueryWrapper<>();
        modelQueryWrapper.eq("id", model.getId());
        Model mdl = modelMapper.selectOne(modelQueryWrapper);
        if (mdl == null) {
            return false;
        }
        modelMapper.updateById(model);
        return true;
    }

    public boolean deleteModel(Integer id) {
        Model mdl = modelMapper.selectById(id);
        if (mdl == null) {
            return false;
        }
        modelMapper.deleteById(id);
        return true;
    }

    public boolean insertModel(Model model) {
        QueryWrapper<Model> modelQueryWrapper = new QueryWrapper<>();
        modelQueryWrapper.eq("id", model.getId());
        Model mdl = modelMapper.selectOne(modelQueryWrapper);
        if (mdl != null) {
            return false;
        }
        modelMapper.insert(model);
        return true;
    }
}
