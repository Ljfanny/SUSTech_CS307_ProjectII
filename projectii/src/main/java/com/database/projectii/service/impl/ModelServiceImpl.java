package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.ModelMapper;
import com.database.projectii.model.Model;
import com.database.projectii.service.ModelService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;

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

    public List<Map<String, Object>> selectModelByAny(Model model) {
        QueryWrapper<Model> modelQueryWrapper = new QueryWrapper<>();
        if (model.getId() != null) {
            modelQueryWrapper.eq("id", model.getId());
        }
        if (model.getNumber() != null) {
            modelQueryWrapper.eq("number", model.getNumber());
        }
        if (model.getModel() != null) {
            modelQueryWrapper.eq("model", model.getModel());
        }
        if (model.getName() != null) {
            modelQueryWrapper.eq("name", model.getName());
        }
        if (model.getUnitPrice() != null) {
            modelQueryWrapper.eq("unit_price", model.getUnitPrice());
        }
        return modelMapper.selectMaps(modelQueryWrapper);
    }

    public boolean deleteModelByAny(Model model) {
        QueryWrapper<Model> modelQueryWrapper = new QueryWrapper<>();
        if (model.getId() != null) {
            modelQueryWrapper.eq("id", model.getId());
        }
        if (model.getNumber() != null) {
            modelQueryWrapper.eq("number", model.getNumber());
        }
        if (model.getModel() != null) {
            modelQueryWrapper.eq("model", model.getModel());
        }
        if (model.getName() != null) {
            modelQueryWrapper.eq("name", model.getName());
        }
        if (model.getUnitPrice() != null) {
            modelQueryWrapper.eq("unit_price", model.getUnitPrice());
        }
        List<Map<String, Object>> models = modelMapper.selectMaps(modelQueryWrapper);
        for (Map<String, Object> map : models) {
            modelMapper.deleteById((int) map.get("id"));
        }
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

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Model> modelQueryWrapper = new QueryWrapper<>();
        modelQueryWrapper.select("*");
        return modelMapper.selectMaps(modelQueryWrapper);
    }
}
