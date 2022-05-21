package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.StaffMapper;
import com.database.projectii.model.Model;
import com.database.projectii.model.Staff;
import com.database.projectii.service.StaffService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffMapper staffMapper;

    public Staff selectStaffById(Integer id) {
        return staffMapper.selectById(id);
    }

    public List<Map<String,Object>> selectAll() {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.select("*");
        return staffMapper.selectMaps(staffQueryWrapper);
    }

    public List<Map<String, Object>> selectAllTypeStaffCount() {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.select("type, count(*) as cnt");
        staffQueryWrapper.groupBy("type");
        return staffMapper.selectMaps(staffQueryWrapper);
    }


    public boolean updateStaff(Staff staff) {
        QueryWrapper<Staff> staffQueryWrapper= new QueryWrapper<>();
        staffQueryWrapper.eq("id", staff.getId());
        Staff stf = staffMapper.selectOne(staffQueryWrapper);
        if (stf == null) {
            return false;
        }
        staffMapper.updateById(staff);
        return true;
    }

    public boolean deleteStaff(Integer id) {
        Staff stf = staffMapper.selectById(id);
        if (stf == null) {
            return false;
        }
        staffMapper.deleteById(id);
        return true;
    }

    public boolean insertStaff(Staff staff) {
        QueryWrapper<Staff> staffQueryWrapper= new QueryWrapper<>();
        staffQueryWrapper.eq("id", staff.getId());
        Staff stf = staffMapper.selectOne(staffQueryWrapper);
        if (stf != null) {
            return false;
        }
        staffMapper.insert(staff);
        return true;
    }
}
