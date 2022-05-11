package com.database.projectii.controller;


import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Model;
import com.database.projectii.model.Staff;
import com.database.projectii.service.impl.StaffServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    @GetMapping("/{id}")
    public Data getById(@PathVariable Integer id) {
        Staff staff = staffServiceImpl.selectOrderById(id);
        if (staff == null) {
            return new Data(null, Message.NOT_SUCCESS);
        }
        String[] result =
            new String[] {String.valueOf(staff.getId()), staff.getName(),
                String.valueOf(staff.getAge()), staff.getGender(),
                staff.getNumber(), staff.getSupplyCenter(), staff.getMobileNumber(),
                staff.getType()};
        return new Data(result, Message.SUCCESS);
    }

    @GetMapping("/getAllStaffCount")
    public Data getAllStaffCount() {
        List<Map<String, Object>> mapList = staffServiceImpl.selectAllTypeStaffCount();
        ArrayList<String[]> result = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            result.add(
                new String[] {String.valueOf(map.get("type")), String.valueOf(map.get("cnt"))});
        }
        System.out.println(result);
        return new Data(result, Message.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public Data DeleteById(@PathVariable Integer id) {
        boolean result = staffServiceImpl.deleteStaff(id);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PostMapping
    public Data insert(@RequestBody Staff staff) {
        boolean result = staffServiceImpl.insertStaff(staff);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PutMapping
    public Data update(@RequestBody Staff staff) {
        boolean result = staffServiceImpl.updateStaff(staff);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

}
