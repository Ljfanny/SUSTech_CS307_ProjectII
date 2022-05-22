package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Enterprise;
import com.database.projectii.service.impl.InventoryServiceImpl;
import com.database.projectii.model.Inventory;
import com.database.projectii.service.impl.OrderServiceImpl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/all")
    public Data getAllInventory() {
        ArrayList<Inventory> inventoryArrayList = new ArrayList<>();
        List<Map<String, Object>> res = inventoryServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            inventoryArrayList.add(new Inventory((Integer) map.get("id"),
                (String) map.get("supply_center"), (String) map.get("product_model"),
                (String) map.get("supply_staff"),
                (Date) map.get("date"), (Integer) map.get("purchase_price"),
                (Integer) map.get("surplus_quantity"), (Integer) map.get("total_quantity")));
        }
        return new Data(inventoryArrayList, Message.SUCCESS);
    }

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
        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
    }

    @GetMapping("/getProductByNumber/{productNumber}")
    public Data getProductByNumber(@PathVariable String productNumber) {
        List<Map<String, Object>> maps = inventoryServiceImpl.selectProductByNumber(productNumber);
        List<Product> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new Product(String.valueOf(map.get("supply_center")),
                String.valueOf(map.get("product_number")),
                String.valueOf(map.get("product_model")),
                String.valueOf(map.get("purchase_price")),
                String.valueOf(map.get("surplus_quantity"))));
        }
        return new Data(result, Message.SUCCESS);
    }

    static class Product {
        private String supplyCenter;
        private String productNumber;
        private String productModel;
        private String purchasePrice;
        private String surplusQuantity;

        public String getSupplyCenter() {
            return supplyCenter;
        }

        public void setSupplyCenter(String supplyCenter) {
            this.supplyCenter = supplyCenter;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        public String getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(String purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getSurplusQuantity() {
            return surplusQuantity;
        }

        public void setSurplusQuantity(String surplusQuantity) {
            this.surplusQuantity = surplusQuantity;
        }

        public Product(String supplyCenter, String productNumber, String productModel,
                       String purchasePrice, String surplusQuantity) {
            this.supplyCenter = supplyCenter;
            this.productNumber = productNumber;
            this.productModel = productModel;
            this.purchasePrice = purchasePrice;
            this.surplusQuantity = surplusQuantity;
        }
    }
}
