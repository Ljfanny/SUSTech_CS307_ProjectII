package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Enterprise;
import com.database.projectii.model.ReturnOrder;
import com.database.projectii.service.impl.InventoryServiceImpl;
import com.database.projectii.model.Inventory;
import com.database.projectii.service.impl.OrderServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public Data getByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "supplyCenter", required = false, defaultValue = "")
            String supplyCenter,
        @RequestParam(value = "productModel", required = false, defaultValue = "")
            String productModel,
        @RequestParam(value = "supplyStaff", required = false, defaultValue = "")
            String supplyStaff,
        @RequestParam(value = "date", required = false, defaultValue = "") String date,
        @RequestParam(value = "purchasePrice", required = false, defaultValue = "")
            Integer purchasePrice,
        @RequestParam(value = "surplusQuantity", required = false, defaultValue = "")
            Integer surplusQuantity) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date dt = null;
        try {
            if (!Objects.equals(date, "")) {
                dt = format.parse(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date rigDt = null;
        if (dt != null) {
            rigDt = new Date(dt.getTime());
        }
        Inventory inventory = new Inventory(id, supplyCenter.equals("") ? null : supplyCenter,
            productModel.equals("") ? null : productModel,
            supplyStaff.equals("") ? null : supplyStaff, rigDt,
            purchasePrice, surplusQuantity, null);
        List<Map<String, Object>> mapList = inventoryServiceImpl.selectInventoryByAny(inventory);
        ArrayList<Inventory> inventories = new ArrayList<>();
        if (mapList.isEmpty()) {
            return new Data(null, Message.SUCCESS);
        } else {
            for (Map<String, Object> map : mapList) {
                inventories.add(new Inventory((Integer) map.get("id"),
                    (String) map.get("supply_center"), (String) map.get("product_model"),
                    (String) map.get("supply_staff"),
                    (Date) map.get("date"), (Integer) map.get("purchase_price"),
                    (Integer) map.get("surplus_quantity"), (Integer) map.get("total_quantity")));
            }
        }
        return new Data(inventories, Message.SUCCESS);
    }

    @DeleteMapping
    public Data DeleteByAny(
        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
        @RequestParam(value = "supplyCenter", required = false, defaultValue = "")
            String supplyCenter,
        @RequestParam(value = "productModel", required = false, defaultValue = "")
            String productModel,
        @RequestParam(value = "supplyStaff", required = false, defaultValue = "")
            String supplyStaff,
        @RequestParam(value = "date", required = false, defaultValue = "") String date,
        @RequestParam(value = "purchasePrice", required = false, defaultValue = "")
            Integer purchasePrice,
        @RequestParam(value = "surplusQuantity", required = false, defaultValue = "")
            Integer surplusQuantity) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date dt = null;
        try {
            if (!Objects.equals(date, "")) {
                dt = format.parse(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date rigDt = null;
        if (dt != null) {
            rigDt = new Date(dt.getTime());
        }
        Inventory inventory = new Inventory(id, supplyCenter.equals("") ? null : supplyCenter,
            productModel.equals("") ? null : productModel,
            supplyStaff.equals("") ? null : supplyStaff, rigDt,
            purchasePrice, surplusQuantity, null);
        boolean result = inventoryServiceImpl.deleteInventoryByAny(inventory);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

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
        long[] res = new long[1];
        res[0] = result;
//        try {
//            FileWriter fileWritter = new FileWriter("output.txt",true);
//            BufferedWriter out = new BufferedWriter(fileWritter);
//            out.write("Q9");
//            out.newLine();
//            out.write(String.valueOf(res[0]));
//            out.newLine();
//            out.flush();
//            out.close();
//            PrintStream printStream = new PrintStream("output.txt");
//            System.setOut(printStream);
//            System.out.println("Q9");
//            System.out.println(String.valueOf(String.valueOf(res[0])));
        try (FileWriter fileWriter = new FileWriter("output.txt", true)) {
            fileWriter.append("Q9\n");
            fileWriter.append(String.valueOf(String.valueOf(res[0]))).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Data(res, Message.SUCCESS);
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
        boolean isFir = true;
        try (FileWriter fileWriter = new FileWriter("output.txt", true)) {
            fileWriter.append("Q12\n");
            for (Product product : result) {
                if (isFir) {
                    fileWriter.append("supply_center ").append("product_number ").append("product_model ")
                        .append("purchase_price ").append("surplus").append("\n");
                    isFir = false;
                }
                fileWriter.append(product.supplyCenter).append(" ");
                fileWriter.append(product.productNumber).append(" ");
                fileWriter.append(product.productModel).append(" ");
                fileWriter.append(String.valueOf(product.purchasePrice)).append(" ");
                fileWriter.append(String.valueOf(product.surplusQuantity)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
