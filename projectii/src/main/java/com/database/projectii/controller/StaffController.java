package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Staff;
import com.database.projectii.service.impl.StaffServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/staffs")
public class StaffController {

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    @GetMapping("")
    public Data getByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "") Integer age,
        @RequestParam(value = "gender", required = false, defaultValue = "") String gender,
        @RequestParam(value = "number", required = false, defaultValue = "") String number,
        @RequestParam(value = "supplyCenter", required = false, defaultValue = "")
            String supplyCenter,
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "")
            String mobileNumber,
        @RequestParam(value = "type", required = false, defaultValue = "") String type) {
        Staff staff =
            new Staff(id, name.equals("") ? null : name, age, gender.equals("") ? null : gender,
                number.equals("") ? null : number, supplyCenter.equals("") ? null : supplyCenter,
                mobileNumber.equals("") ? null : mobileNumber, type.equals("") ? null : type);
        List<Map<String, Object>> mapList = staffServiceImpl.selectStaffByAny(staff);
        if (mapList.isEmpty()) {
            return new Data(null, Message.NOT_SUCCESS);
        }
        ArrayList<Staff> staffs = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            staffs.add(
                new Staff((Integer) map.get("id"), (String) map.get("name"),
                    (Integer) map.get("age"), (String) map.get("gender")
                    , (String) map.get("number"), (String) map.get("supply_center"),
                    (String) map.get("mobile_number"),
                    (String) map.get("type")));
        }
        return new Data(staffs, Message.SUCCESS);
    }

    @GetMapping("/all")
    public Data getAllStaffs() {
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        List<Map<String, Object>> res = staffServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            staffArrayList.add(
                new Staff((Integer) map.get("id"), (String) map.get("name"),
                    (Integer) map.get("age"), (String) map.get("gender")
                    , (String) map.get("number"), (String) map.get("supply_center"),
                    (String) map.get("mobile_number"),
                    (String) map.get("type")));
        }
        return new Data(staffArrayList, Message.SUCCESS);
    }

    @GetMapping("/getAllStaffCount")
    public Data getAllStaffCount() {
        List<Map<String, Object>> mapList = staffServiceImpl.selectAllTypeStaffCount();
        ArrayList<StaffType> result = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            result.add(
                new StaffType(String.valueOf(map.get("type")), Integer.parseInt(
                    String.valueOf(map.get("cnt")))));
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            out.write("Q6");
            out.newLine();
            for (StaffType type : result) {
                out.write(type.type + " " + type.cnt);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Data(result, Message.SUCCESS);
    }

    @DeleteMapping
    public Data DeleteByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "") Integer age,
        @RequestParam(value = "gender", required = false, defaultValue = "") String gender,
        @RequestParam(value = "number", required = false, defaultValue = "") String number,
        @RequestParam(value = "supplyCenter", required = false, defaultValue = "")
            String supplyCenter,
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "")
            String mobileNumber,
        @RequestParam(value = "type", required = false, defaultValue = "") String type) {
        Staff staff =
            new Staff(id, name.equals("") ? null : name, age, gender.equals("") ? null : gender,
                number.equals("") ? null : number, supplyCenter.equals("") ? null : supplyCenter,
                mobileNumber.equals("") ? null : mobileNumber, type.equals("") ? null : type);
        boolean result = staffServiceImpl.deleteStaffByAny(staff);
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

    static class StaffType {
        private String type;
        private int cnt;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }

        private StaffType(String type, int cnt) {
            this.type = type;
            this.cnt = cnt;
        }
    }
}
