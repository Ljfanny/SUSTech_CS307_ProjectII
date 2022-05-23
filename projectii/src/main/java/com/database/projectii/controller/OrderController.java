package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.service.impl.OrderServiceImpl;
import com.database.projectii.model.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/getOrderCount")
    public Data getOrderCount() {
        Object result = orderServiceImpl.selectOrderCount();
        return new Data(result, Message.SUCCESS);
    }

    /**
     * 1. 订单中的商品数量⼤于库存数量。（每个商品型号的库存在不同供应中⼼是不同的）
     * 2. ⼈员类型不是“Salesman"
     */

    @PostMapping
    public Data placeOrder(@RequestBody Order[] orders) {
//        System.out.println(order);
//        boolean result = orderServiceImpl.insert(order);
//        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
        for (Order order : orders) {
            orderServiceImpl.insert(order);
        }
        return new Data(true, Message.SUCCESS);
    }

    @PutMapping
    public Data updateOrder(@RequestBody Order[] orders) {
        boolean isFir = true;
        for (Order order : orders) {
            if (isFir) {
                isFir = false;
                continue;
            }
            boolean rt = orderServiceImpl.updateQuantityEstLod(order);
        }
        return new Data(true, Message.SUCCESS);
    }

    @DeleteMapping
    public Data deleteOrder(@RequestParam("contract") String contract,
                            @RequestParam("salesman") String salesman,
                            @RequestParam("seq") Integer seq) {
        boolean rt = orderServiceImpl.deleteByContractSalesmanSeq(contract, salesman, seq);
        return new Data(true, Message.SUCCESS);
    }

    static class FavoriteProduct {
        private String productModel;
        private Long sum;

        public FavoriteProduct(String productModel, Long sum) {
            this.productModel = productModel;
            this.sum = sum;
        }

        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        public Long getSum() {
            return sum;
        }

        public void setSum(Long sum) {
            this.sum = sum;
        }
    }

    static class CenterAve {
        private String supplyCenter;
        private String average;

        public CenterAve(String supplyCenter, String average) {
            this.supplyCenter = supplyCenter;
            this.average = average;
        }

        public String getSupplyCenter() {
            return supplyCenter;
        }

        public void setSupplyCenter(String supplyCenter) {
            this.supplyCenter = supplyCenter;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }
    }

    @GetMapping("/getFavoriteProductModel")
    public Data getFavoriteProductModel() {
        List<Map<String, Object>> maps = orderServiceImpl.selectFavoriteProductModel();
        List<FavoriteProduct> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new FavoriteProduct(String.valueOf(map.get("product_model")),
                (Long) map.get("sum")));
        }
        return new Data(result, Message.SUCCESS);
    }

    @GetMapping("/getAvgStockByCenter")
    public Data getAvgStockByCenter() {
        List<Map<String, Object>> maps = orderServiceImpl.selectAvgStockByCenter();
        List<CenterAve> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new CenterAve(String.valueOf(map.get("supply_center")),
                String.valueOf(map.get("avg"))));
        }
        return new Data(result, Message.SUCCESS);
    }

    @GetMapping("/getContractInfo/{contractNumber}")
    public Data getContractInfo(@PathVariable String contractNumber) {
        String infos = "";
        List<Order> orderList = orderServiceImpl.selectContractInfo(contractNumber, infos);
        if (orderList == null) {
            return new Data(infos, Message.SUCCESS);
        }
        List<Order> result = new ArrayList<>();
        for (Order order : orderList) {
            result.add(
                new Order(null, null, order.getProductModel(), order.getQuantity(), null,
                    null, order.getEstimatedDeliveryDate()
                    , order.getLodgementDate(), order.getSalesmanNumber(), null));
        }
        return new Data(result, Message.SUCCESS);
    }

    @GetMapping("/all")
    public Data getAllOrders() {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        List<Map<String, Object>> res = orderServiceImpl.selectAll();
        for (Map<String, Object> map : res) {
            orderArrayList.add(new Order((String) map.get("contract_number"),
                (String) map.get("enterprise"), (String) map.get("product_model"),
                (Integer) map.get("quantity"), (String) map.get("contract_manager"),
                (Date) map.get("contract_date"), (Date) map.get("estimated_delivery_date"),
                (Date) map.get("lodgement_date"), (String) map.get("salesman_number"),
                (String) map.get("contract_type")));
        }
        return new Data(orderArrayList, Message.SUCCESS);
    }

    @GetMapping
    public Data getByAny(
        @RequestParam(value = "contractNumber", required = false, defaultValue = "")
            String contractNumber,
        @RequestParam(value = "enterprise", required = false, defaultValue = "") String enterprise,
        @RequestParam(value = "productModel", required = false, defaultValue = "")
            String productModel,
        @RequestParam(value = "quantity", required = false, defaultValue = "")
            Integer quantity,
        @RequestParam(value = "contractManager", required = false, defaultValue = "")
            String contractManager,
        @RequestParam(value = "contractDate", required = false, defaultValue = "")
            String contractDate,
        @RequestParam(value = "estimatedDeliveryDate", required = false, defaultValue = "")
            String estimatedDeliveryDate,
        @RequestParam(value = "lodgementDate", required = false, defaultValue = "")
            String lodgementDate,
        @RequestParam(value = "salesmanNumber", required = false, defaultValue = "")
            String salesmanNumber,
        @RequestParam(value = "contractType", required = false, defaultValue = "")
            String contractType) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date cd = null;
        java.util.Date ed = null;
        java.util.Date ld = null;
        try {
            if (!Objects.equals(contractDate, "")) {
                cd = format.parse(contractDate);
            }
            if (!Objects.equals(estimatedDeliveryDate, "")) {
                ed = format.parse(estimatedDeliveryDate);
            }
            if (!Objects.equals(lodgementDate, "")) {
                ld = format.parse(lodgementDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date rigCd = null;
        Date rigEd = null;
        Date rigLd = null;
        if (cd != null) {
            rigCd = new Date(cd.getTime());
        }
        if (ed != null) {
            rigEd = new Date(ed.getTime());
        }
        if (ld != null) {
            rigLd = new Date(ld.getTime());
        }
        Order order = new Order(
            contractNumber.equals("") ? null : contractNumber,
            enterprise.equals("") ? null : enterprise,
            productModel.equals("") ? null : productModel, quantity,
            contractManager.equals("") ? null : contractManager, rigCd, rigEd, rigLd,
            salesmanNumber.equals("") ? null : salesmanNumber,
            contractType.equals("") ? null : contractType
        );
        List<Map<String, Object>> mapList = orderServiceImpl.selectOrderByAny(order);
        ArrayList<Order> orders = new ArrayList<>();
        if (mapList.isEmpty()) {
            return new Data(null, Message.SUCCESS);
        } else {
            for (Map<String, Object> map : mapList) {
                orders.add(new Order((String) map.get("contract_number"),
                    (String) map.get("enterprise"), (String) map.get("product_model"),
                    (Integer) map.get("quantity"), (String) map.get("contract_manager"),
                    (Date) map.get("contract_date"), (Date) map.get("estimated_delivery_date"),
                    (Date) map.get("lodgement_date"), (String) map.get("salesman_number"),
                    (String) map.get("contract_type")));
            }
        }
        System.out.println(orders);
        return new Data(orders, Message.SUCCESS);
    }

    @DeleteMapping("/diy")
    public Data DeleteByAny(
        @RequestParam(value = "contractNumber", required = false, defaultValue = "")
            String contractNumber,
        @RequestParam(value = "enterprise", required = false, defaultValue = "") String enterprise,
        @RequestParam(value = "productModel", required = false, defaultValue = "")
            String productModel,
        @RequestParam(value = "quantity", required = false, defaultValue = "")
            Integer quantity,
        @RequestParam(value = "contractManager", required = false, defaultValue = "")
            String contractManager,
        @RequestParam(value = "contractDate", required = false, defaultValue = "")
            String contractDate,
        @RequestParam(value = "estimatedDeliveryDate", required = false, defaultValue = "")
            String estimatedDeliveryDate,
        @RequestParam(value = "lodgementDate", required = false, defaultValue = "")
            String lodgementDate,
        @RequestParam(value = "salesmanNumber", required = false, defaultValue = "")
            String salesmanNumber,
        @RequestParam(value = "contractType", required = false, defaultValue = "")
            String contractType) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date cd = null;
        java.util.Date ed = null;
        java.util.Date ld = null;
        try {
            if (!Objects.equals(contractDate, "")) {
                cd = format.parse(contractDate);
            }
            if (!Objects.equals(estimatedDeliveryDate, "")) {
                ed = format.parse(estimatedDeliveryDate);
            }
            if (!Objects.equals(lodgementDate, "")) {
                ld = format.parse(lodgementDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date rigCd = null;
        Date rigEd = null;
        Date rigLd = null;
        if (cd != null) {
            rigCd = new Date(cd.getTime());
        }
        if (ed != null) {
            rigEd = new Date(ed.getTime());
        }
        if (ld != null) {
            rigLd = new Date(ld.getTime());
        }
        Order order = new Order(
            contractNumber.equals("") ? null : contractNumber,
            enterprise.equals("") ? null : enterprise,
            productModel.equals("") ? null : productModel, quantity,
            contractManager.equals("") ? null : contractManager, rigCd, rigEd, rigLd,
            salesmanNumber.equals("") ? null : salesmanNumber,
            contractType.equals("") ? null : contractType
        );
        boolean result = orderServiceImpl.deleteOrderByAny(order);
        String msg = result ? Message.SUCCESS : Message.NOT_SUCCESS;
        return new Data(result, msg);
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

}
