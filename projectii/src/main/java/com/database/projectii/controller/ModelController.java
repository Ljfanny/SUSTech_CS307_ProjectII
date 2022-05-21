package com.database.projectii.controller;


import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Center;
import com.database.projectii.model.Enterprise;
import com.database.projectii.model.Inventory;
import com.database.projectii.model.Model;
import com.database.projectii.model.Staff;
import com.database.projectii.service.impl.ModelServiceImpl;
import java.sql.Date;
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
@RequestMapping("/models")
public class ModelController {

    @Autowired
    private ModelServiceImpl modelServiceImpl;

    @GetMapping("/{id}")
    public Data getById(@PathVariable Integer id) {
        Model model = modelServiceImpl.selectModelById(id);
        if (model == null) {
            return new Data(null, Message.NOT_SUCCESS);
        }
        return new Data(model, Message.SUCCESS);
    }

    @GetMapping("/all")
    public Data getAllModel() {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        List<Map<String, Object>> res = modelServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            modelArrayList.add(new Model((Integer) map.get("id"),
                (String) map.get("number"), (String) map.get("model"),
                (String) map.get("name"), (Integer) map.get("unit_price")));
        }
        return new Data(modelArrayList, Message.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public Data DeleteById(@PathVariable Integer id) {
        boolean result = modelServiceImpl.deleteModel(id);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PostMapping
    public Data insert(@RequestBody Model model) {
        boolean result = modelServiceImpl.insertModel(model);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

    @PutMapping
    public Data update(@RequestBody Model model) {
        boolean result = modelServiceImpl.updateModel(model);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }
}
