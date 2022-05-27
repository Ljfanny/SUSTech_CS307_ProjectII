package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Center;
import com.database.projectii.service.impl.CenterServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/centers")
public class CenterController {

    @Autowired
    private CenterServiceImpl centerServiceImpl;

    // 用任何属性都可以进行查询
    @GetMapping
    public Data getByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        Center center =
            new Center(id, name.equals("") ? null : name);
        List<Map<String, Object>> mapList = centerServiceImpl.selectCenterByAny(center);
        ArrayList<Center> centers = new ArrayList<>();
        if (mapList.isEmpty()) {
            return new Data(null, Message.SUCCESS);
        } else {
            for (Map<String, Object> map : mapList) {
                centers.add(new Center((Integer) map.get("id"), (String) map.get("name")));
            }
        }
        return new Data(centers, Message.SUCCESS);
    }

    @GetMapping("/all")
    public Data getAllCenters() {
        ArrayList<Center> centerArrayList = new ArrayList<>();
        List<Map<String, Object>> res = centerServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            centerArrayList.add(new Center((Integer) map.get("id"), (String) map.get("name")));
        }
        return new Data(centerArrayList, Message.SUCCESS);
    }


    @DeleteMapping
    public Data DeleteByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        Center center =
            new Center(id, name.equals("") ? null : name);
        boolean result = centerServiceImpl.deleteCenterByAny(center);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PostMapping
    public Data insert(@RequestBody Center center) {
        boolean result = centerServiceImpl.insertCenter(center);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PutMapping
    public Data update(@RequestBody Center center) {
        boolean result = centerServiceImpl.updateCenter(center);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }
}
