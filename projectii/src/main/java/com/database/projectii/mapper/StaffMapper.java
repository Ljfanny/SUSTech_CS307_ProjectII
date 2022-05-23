package com.database.projectii.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.database.projectii.model.Staff;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

    @Select("select name from staffs where number = ${number}")
    String selectNameByNumber(@Param("number") String number);
}
