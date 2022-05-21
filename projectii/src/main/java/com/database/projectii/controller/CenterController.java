package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Center;
import com.database.projectii.model.Staff;
import com.database.projectii.service.impl.CenterServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/centers")
public class CenterController {

    @Autowired
    private CenterServiceImpl centerServiceImpl;

    @GetMapping("/{id}")
    public Data getById(@PathVariable Integer id) {
        Center center = centerServiceImpl.selectCenterById(id);
        if (center == null) {
            return new Data(null, Message.SUCCESS);
        }
        return new Data(center, Message.SUCCESS);
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


    @DeleteMapping("/{id}")
    public Data DeleteById(@PathVariable Integer id) {
        boolean result = centerServiceImpl.deleteCenter(id);
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
