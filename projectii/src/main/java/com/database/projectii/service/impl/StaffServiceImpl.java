package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.StaffMapper;
import com.database.projectii.model.Center;
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


    public List<Map<String, Object>> selectAll() {
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
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("id", staff.getId());
        Staff stf = staffMapper.selectOne(staffQueryWrapper);
        if (stf == null) {
            return false;
        }
        staffMapper.updateById(staff);
        return true;
    }

    public List<Map<String, Object>> selectStaffByAny(Staff staff) {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        if (staff.getId() != null) {
            staffQueryWrapper.eq("id", staff.getId());
        }
        if (staff.getName() != null) {
            staffQueryWrapper.eq("name", staff.getName());
        }
        if (staff.getAge() != null) {
            staffQueryWrapper.eq("age", staff.getAge());
        }
        if (staff.getGender() != null) {
            staffQueryWrapper.eq("gender", staff.getGender());
        }
        if (staff.getNumber() != null) {
            staffQueryWrapper.eq("number", staff.getNumber());
        }
        if (staff.getSupplyCenter() != null) {
            staffQueryWrapper.eq("supply_center", staff.getSupplyCenter());
        }
        if (staff.getMobileNumber() != null) {
            staffQueryWrapper.eq("mobile_number", staff.getMobileNumber());
        }
        if (staff.getType() != null) {
            staffQueryWrapper.eq("type", staff.getType());
        }
        return staffMapper.selectMaps(staffQueryWrapper);
    }

    public boolean deleteStaffByAny(Staff staff) {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        if (staff.getId() != null) {
            staffQueryWrapper.eq("id", staff.getId());
        }
        if (staff.getName() != null) {
            staffQueryWrapper.eq("name", staff.getName());
        }
        if (staff.getAge() != null) {
            staffQueryWrapper.eq("age", staff.getAge());
        }
        if (staff.getGender() != null) {
            staffQueryWrapper.eq("gender", staff.getGender());
        }
        if (staff.getNumber() != null) {
            staffQueryWrapper.eq("number", staff.getNumber());
        }
        if (staff.getSupplyCenter() != null) {
            staffQueryWrapper.eq("supply_center", staff.getSupplyCenter());
        }
        if (staff.getMobileNumber() != null) {
            staffQueryWrapper.eq("mobile_number", staff.getMobileNumber());
        }
        if (staff.getType() != null) {
            staffQueryWrapper.eq("type", staff.getType());
        }
        List<Map<String, Object>> staffs = staffMapper.selectMaps(staffQueryWrapper);
        for (Map<String, Object> map : staffs) {
            staffMapper.deleteById((int) map.get("id"));
        }
        return true;
    }

    public boolean insertStaff(Staff staff) {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("id", staff.getId());
        Staff stf = staffMapper.selectOne(staffQueryWrapper);
        if (stf != null) {
            return false;
        }
        staffMapper.insert(staff);
        return true;
    }
}
