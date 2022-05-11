package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.service.impl.InventoryServiceImpl;
import com.database.projectii.model.Inventory;
import com.database.projectii.service.impl.OrderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/getNeverSoldProductCount")
    public Data getNeverSoldProductCount() {
        long result = inventoryServiceImpl.selectNeverSoldProductCount();
        return new Data(result, Message.SUCCESS);
    }

    /**
     * 1. 供应中⼼与⼈员所在的供应中⼼对应不上
     * 2. ⼈员的类型不是“supply_staff"
     * 3. 供应中⼼不存在
     * 4. 产品不存在
     * 5. ⼈员不存在
     */

    @PostMapping
    public Data stockIn(@RequestBody Inventory inventory) {
        boolean result = inventoryServiceImpl.insert(inventory);
        System.out.println(inventory);
        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
    }

    @GetMapping("/getProductByNumber/{productNumber}")
    public Data getProductByNumber(@PathVariable String productNumber) {
        List<Map<String, Object>> maps = inventoryServiceImpl.selectProductByNumber(productNumber);
        List<String[]> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new String[] {String.valueOf(map.get("supply_center")),
                String.valueOf(map.get("product_number")),
                String.valueOf(map.get("product_model")),
                String.valueOf(map.get("purchase_price")),
                String.valueOf(map.get("surplus_quantity"))});
        }
        return new Data(result, Message.SUCCESS);
    }
}
