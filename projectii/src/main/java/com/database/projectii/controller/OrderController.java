package com.database.projectii.controller;

import com.database.projectii.controller.transmission.Data;
import com.database.projectii.controller.transmission.Message;
import com.database.projectii.service.impl.OrderServiceImpl;
import com.database.projectii.model.Order;
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
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/getOrderCount")
    public Data getOrderCount() {
        long result = orderServiceImpl.selectOrderCount();
        return new Data(result, Message.SUCCESS);
    }

    /**
     * 1. 订单中的商品数量⼤于库存数量。（每个商品型号的库存在不同供应中⼼是不同的）
     * 2. ⼈员类型不是“Salesman"
     */

    @PostMapping
    public Data placeOrder(@RequestBody Order order) {
        System.out.println(order);
        boolean result = orderServiceImpl.insert(order);
        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
    }

    @PutMapping
    public Data updateOrder(@RequestBody Order order) {
        boolean result = orderServiceImpl.updateQuantitEstLod(order);
        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
    }

    @DeleteMapping
    public Data deleteOrder(String contract, String salesman, Integer seq) {
        boolean result = orderServiceImpl.deleteByContractSalesmanSeq(contract, salesman, seq);
        return new Data(result, result ? Message.SUCCESS : Message.NOT_SUCCESS);
    }

    @GetMapping("/getFavoriteProductModel")
    public Data getFavoriteProductModel() {
        List<Map<String, Object>> maps = orderServiceImpl.selectFavoriteProductModel();
        List<String[]> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new String[] {String.valueOf(map.get("product_model")),
                String.valueOf(map.get("sum"))});
        }
        return new Data(result, Message.SUCCESS);
    }

    @GetMapping("/getAvgStockByCenter")
    public Data getAvgStockByCenter() {
        List<Map<String, Object>> maps = orderServiceImpl.selectAvgStockByCenter();
        List<String[]> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(new String[] {String.valueOf(map.get("supply_center")),
                String.valueOf(map.get("avg"))});
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
        List<String[]> result = new ArrayList<>();
        for (Order order : orderList) {
            result.add(new String[] {order.getProductModel(), order.getSalesmanNumber(),
                String.valueOf(order.getQuantity())
                , String.valueOf(order.getEstimatedDeliveryDate())
                , String.valueOf(order.getLodgementDate())});
        }
        return new Data(result, Message.SUCCESS);
    }

}
