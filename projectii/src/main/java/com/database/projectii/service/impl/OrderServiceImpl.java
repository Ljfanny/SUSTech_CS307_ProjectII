package com.database.projectii.service.impl;

import com.alibaba.druid.pool.ha.selector.StickyDataSourceHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.database.projectii.mapper.*;
import com.database.projectii.model.*;
import com.database.projectii.service.OrderService;
import com.database.projectii.service.property.StaffType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    /**
     * 1. 订单中的商品数量⼤于库存数量。（每个商品型号的库存在不同供应中⼼是不同的）
     * 2. ⼈员类型不是“Salesman"
     */

    public boolean insert(Order order) {
        Staff staff = selectStaffBySalesmanNumber(order);
        if (staff == null) {
            return false;
        }
        if (!StaffType.SALESMAN.equals(staff.getType())) {
            return false;
        }
        int cnt = 0;
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(order);
        for (Inventory item : inventoryList) {
            cnt += item.getSurplusQuantity();
        }
        if (order.getQuantity() > cnt) {
            return false;
        }
        int quantity = order.getQuantity();
        for (Inventory item : inventoryList) {
//            LambdaUpdateWrapper<Inventory> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            if (item.getSurplusQuantity() >= quantity) {
//                lambdaUpdateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                    .set(Inventory::getSurplusQuantity, item.getSurplusQuantity() - quantity);
                item.setSurplusQuantity(item.getSurplusQuantity() - quantity);
//                inventoryMapper.update(null, lambdaUpdateWrapper);
                inventoryMapper.updateById(item);
                break;
            } else {
                quantity -= item.getSurplusQuantity();
//                lambdaUpdateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                    .set(Inventory::getSurplusQuantity, 0);
//                inventoryMapper.update(null, lambdaUpdateWrapper);
                item.setSurplusQuantity(0);
                inventoryMapper.updateById(item);
            }
        }
        orderMapper.insert(order);
        return true;
    }

    public boolean updateQuantityEstLod(Order order) {
        LambdaUpdateWrapper<Order> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_number", order.getContractNumber())
            .eq("product_model", order.getProductModel())
            .eq("salesman_number", order.getSalesmanNumber());
        Order orderPrev = orderMapper.selectOne(queryWrapper);
        int orderQuantityPrev = orderPrev.getQuantity();
        int orderQuantityNext = order.getQuantity();
        orderPrev.setQuantity(order.getQuantity());
        orderPrev.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        orderPrev.setLodgementDate(order.getLodgementDate());
        String center = selectEnterpriseCenterByName(orderPrev);
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(orderPrev);
        if (orderQuantityPrev < orderQuantityNext) {
            int cnt = 0;
            int differential = orderQuantityNext - orderQuantityPrev;
            for (Inventory item : inventoryList) {
                cnt += item.getSurplusQuantity();
            }
            if (cnt < differential) {
                return false;
            }
            for (Inventory item : inventoryList) {
//                LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
                if (item.getSurplusQuantity() >= differential) {
//                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                        .set(Inventory::getSurplusQuantity,
//                            item.getSurplusQuantity() - differential);
                    item.setSurplusQuantity(item.getSurplusQuantity() - differential);
                    inventoryMapper.updateById(item);
                    break;
                } else {
                    differential -= item.getSurplusQuantity();
//                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                        .set(Inventory::getSurplusQuantity, 0);
//                    inventoryMapper.update(null, updateWrapper);
                    item.setSurplusQuantity(0);
                    inventoryMapper.updateById(item);
                }
            }
        } else if (orderQuantityPrev > orderQuantityNext) {
            int differential = orderQuantityPrev - orderQuantityNext;
            for (Inventory item : inventoryList) {
//                LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
                if (item.getTotalQuantity() - item.getSurplusQuantity() >= differential) {
//                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                        .set(Inventory::getSurplusQuantity,
//                            item.getSurplusQuantity() + differential);
//                    inventoryMapper.update(null, updateWrapper);
                    item.setSurplusQuantity(item.getSurplusQuantity() + differential);
                    inventoryMapper.updateById(item);
                    break;
                } else {
                    differential -= (item.getTotalQuantity() - item.getSurplusQuantity());
//                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
//                        .set(Inventory::getSurplusQuantity, item.getTotalQuantity());
//                    inventoryMapper.update(null, updateWrapper);
                    item.setSurplusQuantity(item.getTotalQuantity());
                    inventoryMapper.updateById(item);
                }
            }
        }
        if (orderQuantityNext == 0) {
            orderMapper.deleteById(orderPrev);
            QueryWrapper<Order> queryContract = new QueryWrapper<>();
            queryContract.eq("contract_number", order.getContractNumber());
            List<Order> orderList = orderMapper.selectList(queryContract);
            if (orderList.size() == 0) {
                contractMapper.insert(
                    new Contract(orderPrev.getContractNumber(), orderPrev.getContractManager(),
                        orderPrev.getContractDate(), orderPrev.getEnterprise(), center));
            }
            return true;
        }
//        orderMapper.updateById(orderPrev);
        lambdaUpdateWrapper.eq(Order::getContractNumber, order.getContractNumber())
            .eq(Order::getProductModel, order.getProductModel())
            .eq(Order::getSalesmanNumber, order.getSalesmanNumber())
            .set(Order::getQuantity, order.getQuantity())
            .set(Order::getEstimatedDeliveryDate, order.getEstimatedDeliveryDate())
            .set(Order::getLodgementDate, order.getLodgementDate());
        orderMapper.update(null, lambdaUpdateWrapper);
        return true;
    }

    public boolean deleteByContractSalesmanSeq(String contract, String salesman, Integer seq) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_number", contract)
            .eq("salesman_number", salesman);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        Collections.sort(orderList);
        int num = 0;
        for (int i = 0; i < orderList.size(); i++) {
            if (seq - 1 == i) {
                num = i;
                Map<String, Object> columnMap = new HashMap<String, Object>();
                columnMap.put("contract_number", orderList.get(i).getContractNumber());
                columnMap.put("enterprise", orderList.get(i).getEnterprise());
                columnMap.put("product_model", orderList.get(i).getProductModel());
                columnMap.put("quantity", orderList.get(i).getQuantity());
                columnMap.put("contract_manager", orderList.get(i).getContractManager());
                columnMap.put("contract_date", orderList.get(i).getContractDate());
                columnMap.put("estimated_delivery_date",
                    orderList.get(i).getEstimatedDeliveryDate());
                columnMap.put("lodgement_date", orderList.get(i).getLodgementDate());
                columnMap.put("salesman_number", orderList.get(i).getSalesmanNumber());
                columnMap.put("contract_type", orderList.get(i).getContractType());
                System.out.println(orderList.get(i));
                orderMapper.deleteByMap(columnMap);
                break;
            }
        }
        Order order = orderList.get(num);
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(order);
        String center = selectEnterpriseCenterByName(order);
        int quantity = order.getQuantity();
        for (Inventory item : inventoryList) {
//            LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
            if (item.getTotalQuantity() - item.getSurplusQuantity() >= quantity) {
//                updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                    .eq(Inventory::getSupplyCenter, center)
//                    .set(Inventory::getSurplusQuantity,
//                      item.getSurplusQuantity() + quantity);
//                inventoryMapper.update(null, updateWrapper);
                item.setSurplusQuantity(item.getSurplusQuantity() + quantity);
                inventoryMapper.updateById(item);
                break;
            } else {
                quantity -= (item.getTotalQuantity() - item.getSurplusQuantity());
//                  updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
//                    .eq(Inventory::getSupplyCenter, center)
//                    .set(Inventory::getSurplusQuantity, item.getTotalQuantity());
                item.setSurplusQuantity(item.getTotalQuantity());
                inventoryMapper.updateById(item);
            }
        }
        QueryWrapper<Order> queryContract = new QueryWrapper<>();
        queryContract.eq("contract_number", order.getContractNumber());
        List<Order> orders = orderMapper.selectList(queryContract);
        if (orders.size() == 0) {
            contractMapper.insert(
                new Contract(order.getContractNumber(), order.getContractManager(),
                    order.getContractDate(), order.getEnterprise(), center));
        }
        return true;
    }

    private Staff selectStaffBySalesmanNumber(Order order) {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("number", order.getSalesmanNumber());
        return staffMapper.selectOne(wrapper);
    }

    private Staff selectStaffByManagerNumber(Order order) {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.eq("number", order.getContractManager());
        return staffMapper.selectOne(wrapper);
    }

    private String selectEnterpriseCenterByName(Order order) {
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();
        wrapper.eq("name", order.getEnterprise());
        return enterpriseMapper.selectOne(wrapper).getSupplyCenter();
    }

    private List<Inventory> selectInventoriesByModelAndCenter(Order order) {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", order.getEnterprise());
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper);
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("product_model", order.getProductModel())
            .eq("supply_center", enterprise.getSupplyCenter());
        return inventoryMapper.selectList(inventoryQueryWrapper);
    }

    public Object selectOrderCount() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return orderMapper.selectObjs(queryWrapper);
    }

    public List<Map<String, Object>> selectFavoriteProductModel() {
        return orderMapper.selectFavoriteProductModel();
    }

    public List<Map<String, Object>> selectAvgStockByCenter() {
        return orderMapper.selectAvgStockByCenter();
    }

    public List<ReturnOrder> selectContractInfo(String contractNumber) {
        List<ReturnOrder> orders = new ArrayList<>();
        List<Map<String, Object>> mapList = orderMapper.selectContractInfo(contractNumber);
        if (mapList.isEmpty()) {
            List<Map<String, Object>> cons = contractMapper.selectContractInfo(contractNumber);
            for (Map<String, Object> con : cons) {
                orders.add(
                    new ReturnOrder((String) con.get("number"), (String) con.get("manager"),
                        (String) con.get("enterprise"), (String) con.get("center"),
                        null, null, null, null, null, null));
            }
            return orders;
        } else {
            for (Map<String, Object> map : mapList) {
                orders.add(new ReturnOrder((String) map.get("contract_number"),
                    (String) map.get("contract_manager"),
                    (String) map.get("enterprise"), (String) map.get("supply_center"),
                    (String) map.get("product_model"),
                    (String) map.get("salesman_number"),
                    (Integer) map.get("quantity"),
                    (Integer) map.get("unit_price"),
                    (java.sql.Date) map.get("estimated_delivery_date"),
                    (java.sql.Date) map.get("lodgement_date")));
            }
            return orders;
        }
    }

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.select("*");
        return orderMapper.selectMaps(orderQueryWrapper);
    }

    public List<Map<String, Object>> selectOrderByAny(Order order) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        boolean isCon = false;
        boolean isEst = false;
        boolean isLod = false;
        boolean isOther = false;
        if (order.getContractNumber() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_number", order.getContractNumber());
        }
        if (order.getEnterprise() != null) {
            isOther = true;
            orderQueryWrapper.eq("enterprise", order.getEnterprise());
        }
        if (order.getProductModel() != null) {
            isOther = true;
            orderQueryWrapper.eq("product_model", order.getProductModel());
        }
        if (order.getQuantity() != null) {
            isOther = true;
            orderQueryWrapper.eq("quantity", order.getQuantity());
        }
        if (order.getContractManager() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_manager", order.getContractManager());
        }
        if (order.getContractDate() != null) {
            isCon = true;
        }
        if (order.getEstimatedDeliveryDate() != null) {
            isEst = true;
        }
        if (order.getLodgementDate() != null) {
            isLod = true;
        }
        if (order.getSalesmanNumber() != null) {
            isOther = true;
            orderQueryWrapper.eq("salesman_number", order.getSalesmanNumber());
        }
        if (order.getContractType() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_type", order.getContractType());
        }
        List<Map<String, Object>> orders;
        if (isOther) {
            orders = orderMapper.selectMaps(orderQueryWrapper);
        } else {
            orders = selectAll();
        }
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> map : orders) {
            boolean conCheck = false;
            boolean estCheck = false;
            boolean lodCheck = false;
            if (isCon &&
                order.getContractDate().getTime() == ((Date) map.get("contract_date")).getTime()) {
                conCheck = true;
            }
            if (isEst &&
                order.getEstimatedDeliveryDate().getTime() ==
                    ((Date) map.get("estimated_delivery_date")).getTime()) {
                estCheck = true;
            }
            if (isLod &&
                order.getLodgementDate().getTime() ==
                    ((Date) map.get("lodgement_date")).getTime()) {
                lodCheck = true;
            }
            if (isCon == conCheck && isEst == estCheck && isLod == lodCheck) {
                res.add(map);
            }
        }
        return res;
    }

    public boolean deleteOrderByAny(Order order) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        boolean isCon = false;
        boolean isEst = false;
        boolean isLod = false;
        boolean isOther = false;
        if (order.getContractNumber() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_number", order.getContractNumber());
        }
        if (order.getEnterprise() != null) {
            isOther = true;
            orderQueryWrapper.eq("enterprise", order.getEnterprise());
        }
        if (order.getProductModel() != null) {
            isOther = true;
            orderQueryWrapper.eq("product_model", order.getProductModel());
        }
        if (order.getQuantity() != null) {
            isOther = true;
            orderQueryWrapper.eq("quantity", order.getQuantity());
        }
        if (order.getContractManager() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_manager", order.getContractManager());
        }
        if (order.getContractDate() != null) {
            isCon = true;
        }
        if (order.getEstimatedDeliveryDate() != null) {
            isEst = true;
        }
        if (order.getLodgementDate() != null) {
            isLod = true;
        }
        if (order.getSalesmanNumber() != null) {
            isOther = true;
            orderQueryWrapper.eq("salesman_number", order.getSalesmanNumber());
        }
        if (order.getContractType() != null) {
            isOther = true;
            orderQueryWrapper.eq("contract_type", order.getContractType());
        }
        List<Map<String, Object>> orders;
        if (isOther) {
            orders = orderMapper.selectMaps(orderQueryWrapper);
        } else {
            orders = selectAll();
        }
        for (Map<String, Object> map : orders) {
            boolean conCheck = false;
            boolean estCheck = false;
            boolean lodCheck = false;
            if (isCon &&
                order.getContractDate().getTime() == ((Date) map.get("contract_date")).getTime()) {
                conCheck = true;
            }
            if (isEst &&
                order.getEstimatedDeliveryDate().getTime() ==
                    ((Date) map.get("estimated_delivery_date")).getTime()) {
                estCheck = true;
            }
            if (isLod &&
                order.getLodgementDate().getTime() ==
                    ((Date) map.get("lodgement_date")).getTime()) {
                lodCheck = true;
            }
            if (isCon == conCheck && isEst == estCheck && isLod == lodCheck) {
//                orderMapper.deleteById(new Order((String) map.get("contract_number"),
//                    (String) map.get("enterprise"), (String) map.get("product_model"),
//                    (Integer) map.get("quantity"),
//                    (String) map.get("contract_manager"), (java.sql.Date) map.get("contract_date"),
//                    (java.sql.Date) map.get("estimated_delivery_date"),
//                    (java.sql.Date) map.get("lodgement_date"), (String) map.get("salesman_number"),
//                    (String) map.get("contract_type")));
                orderMapper.deleteByMap(map);
            }
        }
        return true;
    }
}
