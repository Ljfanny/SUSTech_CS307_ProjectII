package com.database.projectii.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.database.projectii.model.Model;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModelMapper extends BaseMapper<Model> {
}
