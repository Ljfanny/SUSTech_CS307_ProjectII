package com.database.projectii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.database.projectii.mapper.ContractMapper;
import com.database.projectii.mapper.InventoryMapper;
import com.database.projectii.mapper.OrderMapper;
import com.database.projectii.mapper.StaffMapper;
import com.database.projectii.model.Contract;
import com.database.projectii.model.Inventory;
import com.database.projectii.model.Model;
import com.database.projectii.model.Order;
import com.database.projectii.model.Staff;
import com.database.projectii.service.OrderService;
import com.database.projectii.service.property.StaffType;
import java.util.Collections;
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



    /**
     * 1. 订单中的商品数量⼤于库存数量。（每个商品型号的库存在不同供应中⼼是不同的）
     * 2. ⼈员类型不是“Salesman"
     */

    public boolean insert(Order order) {
        Staff staff = selectStaffBySalesmanNumber(order);
        if (!staff.getType().equals(StaffType.SALESMAN)) {
            return false;
        }
        int cnt = 0;
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(order);
        for (Inventory item : inventoryList) {
            cnt += item.getSurplusQuantity();
        }
        if (order.getQuantity() < cnt) {
            return false;
        }
        int quantity = order.getQuantity();
        for (Inventory item : inventoryList) {
            LambdaUpdateWrapper<Inventory> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            if (item.getSurplusQuantity() >= quantity) {
                lambdaUpdateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                    .set(Inventory::getSurplusQuantity, item.getSurplusQuantity() - quantity);
                inventoryMapper.update(null, lambdaUpdateWrapper);
                break;
            } else {
                quantity -= item.getSurplusQuantity();
                lambdaUpdateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                    .set(Inventory::getSurplusQuantity, 0);
                inventoryMapper.update(null, lambdaUpdateWrapper);
            }
        }
        orderMapper.insert(order);
        return true;
    }

    public boolean updateQuantitEstLod(Order order) {
        LambdaUpdateWrapper<Order> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_number", order.getContractNumber())
            .eq("product_model", order.getProductModel())
            .eq("salesman_number", order.getSalesmanNumber());
        Order orderPrev = orderMapper.selectOne(queryWrapper);
        Staff staff = selectStaffBySalesmanNumber(order);
        Staff manager = selectStaffByManagerNumber(order);
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(order);
        if (order.getQuantity() == 0) {
            QueryWrapper<Order> queryContract = new QueryWrapper<>();
            queryContract.eq("contract_number", order.getContractNumber());
            List<Order> orderList = orderMapper.selectList(queryContract);
            if (orderList.size() == 1) {
                contractMapper.insert(
                    new Contract(order.getContractNumber(), order.getContractManager(),
                        order.getContractDate(), order.getEnterprise(), manager.getSupplyCenter()));
                orderMapper.deleteById(orderPrev);
            }
        }
        int orderQuantityPrev = orderPrev.getQuantity();
        if (orderQuantityPrev < order.getQuantity()) {
            int cnt = 0;
            int differential = order.getQuantity() - orderQuantityPrev;
            for (Inventory item : inventoryList) {
                cnt += item.getSurplusQuantity();
            }
            if (cnt < differential) {
                return false;
            }
            for (Inventory item : inventoryList) {
                LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
                if (item.getSurplusQuantity() >= differential) {
                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                        .set(Inventory::getSurplusQuantity,
                            item.getSurplusQuantity() - differential);
                    inventoryMapper.update(null, updateWrapper);
                    break;
                } else {
                    differential -= item.getSurplusQuantity();
                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                        .set(Inventory::getSurplusQuantity, 0);
                    inventoryMapper.update(null, updateWrapper);
                }
            }
        } else if (orderQuantityPrev > order.getQuantity()) {
            int differential = orderQuantityPrev - order.getQuantity();
            for (Inventory item : inventoryList) {
                LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
                if (item.getTotalQuantity() - item.getSurplusQuantity() <= differential) {
                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                        .set(Inventory::getSurplusQuantity,
                            item.getSurplusQuantity() + differential);
                    inventoryMapper.update(null, updateWrapper);
                    break;
                } else {
                    differential -= (item.getTotalQuantity() - item.getSurplusQuantity());
                    updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                        .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                        .set(Inventory::getSurplusQuantity, item.getTotalQuantity());
                    inventoryMapper.update(null, updateWrapper);
                }
            }
        }
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
                orderMapper.deleteById(orderList.get(i));
                break;
            }
        }
        Order order = orderList.get(num);
        List<Inventory> inventoryList = selectInventoriesByModelAndCenter(order);
        Staff staff = selectStaffBySalesmanNumber(order);
        Staff manager = selectStaffByManagerNumber(order);
        int quantity = order.getQuantity();
        for (Inventory item : inventoryList) {
            LambdaUpdateWrapper<Inventory> updateWrapper = new LambdaUpdateWrapper<>();
            if (item.getTotalQuantity() - item.getSurplusQuantity() <= quantity) {
                updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                    .set(Inventory::getSurplusQuantity,
                        item.getSurplusQuantity() + quantity);
                inventoryMapper.update(null, updateWrapper);
                break;
            } else {
                quantity -= (item.getTotalQuantity() - item.getSurplusQuantity());
                updateWrapper.eq(Inventory::getProductModel, order.getProductModel())
                    .eq(Inventory::getSupplyCenter, staff.getSupplyCenter())
                    .set(Inventory::getSurplusQuantity, item.getTotalQuantity());
                inventoryMapper.update(null, updateWrapper);
            }
        }
        QueryWrapper<Order> queryContract = new QueryWrapper<>();
        queryContract.eq("contract_number", order.getContractNumber());
        List<Order> orders = orderMapper.selectList(queryContract);
        if (orders.size() == 0) {
            contractMapper.insert(
                new Contract(order.getContractNumber(), order.getContractManager(),
                    order.getContractDate(), order.getEnterprise(), manager.getSupplyCenter()));
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

    private List<Inventory> selectInventoriesByModelAndCenter(Order order) {
        Staff staff = selectStaffBySalesmanNumber(order);
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_model", order.getProductModel())
            .eq("supply_center", staff.getSupplyCenter());
        return inventoryMapper.selectList(queryWrapper);
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

    public List<Order> selectContractInfo(String contractNumber, String infos) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("contract_number", contractNumber);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        StringBuilder stringBuilder = new StringBuilder();
        if (order == null){
            QueryWrapper<Contract> contractQueryWrapper = new QueryWrapper<>();
            contractQueryWrapper.eq("contract_number", contractNumber);
            Contract contract = contractMapper.selectOne(contractQueryWrapper);
            stringBuilder.append(contract.getNumber());
            stringBuilder.append(",").append(contract.getEnterprise());
            stringBuilder.append(",").append(contract.getManager());
            stringBuilder.append(",").append(contract.getCenter());
            infos = stringBuilder.toString();
            return null;
        }else {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("contract_number", contractNumber);
            return orderMapper.selectList(queryWrapper);
        }
    }

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.select("*");
        return orderMapper.selectMaps(orderQueryWrapper);
    }

    public List<Map<String, Object>> selectOrderByAny(Order order) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        if (order.getContractNumber()!= null) {
            orderQueryWrapper.eq("contractNumber", order.getContractNumber());
        }
        if (order.getEnterprise() != null) {
            orderQueryWrapper.eq("enterprise", order.getEnterprise());
        }
        if (order.getProductModel() != null) {
            orderQueryWrapper.eq("productModel", order.getProductModel());
        }
        if (order.getQuantity() != null) {
            orderQueryWrapper.eq("quantity", order.getQuantity());
        }
        if (order.getContractManager() != null) {
            orderQueryWrapper.eq("contractManager", order.getContractManager());
        }
        if (order.getContractDate() != null) {
            orderQueryWrapper.eq("contractDate", order.getContractDate());
        }
        if (order.getEstimatedDeliveryDate() != null) {
            orderQueryWrapper.eq("estimatedDeliveryDate", order.getEstimatedDeliveryDate());
        }
        if (order.getLodgementDate() != null) {
            orderQueryWrapper.eq("lodgementDate", order.getLodgementDate());
        }
        if (order.getSalesmanNumber() != null) {
            orderQueryWrapper.eq("salesmanNumber", order.getSalesmanNumber());
        }
        if (order.getContractType() != null) {
            orderQueryWrapper.eq("contractType", order.getContractType());
        }
        return orderMapper.selectMaps(orderQueryWrapper);
    }

    public boolean deleteOrderByAny(Order order) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        if (order.getContractNumber()!= null) {
            orderQueryWrapper.eq("contractNumber", order.getContractNumber());
        }
        if (order.getEnterprise() != null) {
            orderQueryWrapper.eq("enterprise", order.getEnterprise());
        }
        if (order.getProductModel() != null) {
            orderQueryWrapper.eq("productModel", order.getProductModel());
        }
        if (order.getQuantity() != null) {
            orderQueryWrapper.eq("quantity", order.getQuantity());
        }
        if (order.getContractManager() != null) {
            orderQueryWrapper.eq("contractManager", order.getContractManager());
        }
        if (order.getContractDate() != null) {
            orderQueryWrapper.eq("contractDate", order.getContractDate());
        }
        if (order.getEstimatedDeliveryDate() != null) {
            orderQueryWrapper.eq("estimatedDeliveryDate", order.getEstimatedDeliveryDate());
        }
        if (order.getLodgementDate() != null) {
            orderQueryWrapper.eq("lodgementDate", order.getLodgementDate());
        }
        if (order.getSalesmanNumber() != null) {
            orderQueryWrapper.eq("salesmanNumber", order.getSalesmanNumber());
        }
        if (order.getContractType() != null) {
            orderQueryWrapper.eq("contractType", order.getContractType());
        }
        List<Map<String, Object>> orders = orderMapper.selectMaps(orderQueryWrapper);
        for (Map<String, Object> map : orders) {
            orderMapper.deleteById((int) map.get("id"));
        }
        return true;
    }
}
