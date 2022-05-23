package com.database.projectii.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.database.projectii.mapper.CenterMapper;
import com.database.projectii.mapper.InventoryMapper;
import com.database.projectii.mapper.ModelMapper;
import com.database.projectii.mapper.StaffMapper;
import com.database.projectii.model.Center;
import com.database.projectii.model.Enterprise;
import com.database.projectii.model.Inventory;
import com.database.projectii.model.Model;
import com.database.projectii.model.Order;
import com.database.projectii.model.Staff;
import com.database.projectii.service.InventoryService;
import com.database.projectii.service.property.StaffType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private ModelMapper modelMapper;

    public boolean insert(Inventory inventory) {
        inventory.setSurplusQuantity(inventory.getTotalQuantity());
        QueryWrapper<Staff> wrapperStaff = new QueryWrapper<>();
        QueryWrapper<Center> wrapperCenter = new QueryWrapper<>();
        QueryWrapper<Model> wrapperModel = new QueryWrapper<>();
        wrapperStaff.eq("number", inventory.getSupplyStaff());
        wrapperCenter.eq("name", inventory.getSupplyCenter());
        wrapperModel.eq("model", inventory.getProductModel());
        Staff staff = staffMapper.selectOne(wrapperStaff);
        Center center = centerMapper.selectOne(wrapperCenter);
        Model model = modelMapper.selectOne(wrapperModel);
        if (staff == null) {
            return false;
        }
        if (center == null) {
            return false;
        }
        if (model == null) {
            return false;
        }
        if (!staff.getType().equals(StaffType.STAFF)) {
            return false;
        }
        if (!inventory.getSupplyCenter().equals(staff.getSupplyCenter())) {
            return false;
        }
        inventoryMapper.insert(inventory);
        return true;
    }

    public long selectNeverSoldProductCount() {
        return inventoryMapper.selectNeverSoldProductCount();
    }

    public List<Map<String, Object>> selectProductByNumber(String productNumber) {
        return inventoryMapper.selectProductByNumber(productNumber);
    }

    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.select("*");
        return inventoryMapper.selectMaps(inventoryQueryWrapper);
    }

    public List<Map<String, Object>> selectInventoryByAny(Inventory inventory) {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        boolean isDate = false;
        boolean isOther = false;
        if (inventory.getId() != null) {
            inventoryQueryWrapper.eq("id", inventory.getId());
            isOther = true;
        }
        if (inventory.getSupplyCenter() != null) {
            inventoryQueryWrapper.eq("supplyCenter", inventory.getSupplyCenter());
            isOther = true;
        }
        if (inventory.getProductModel() != null) {
            inventoryQueryWrapper.eq("ProductModel", inventory.getProductModel());
            isOther = true;
        }
        if (inventory.getSupplyStaff() != null) {
            inventoryQueryWrapper.eq("supplyStaff", inventory.getSupplyStaff());
            isOther = true;
        }
        if (inventory.getDate() != null) {
//            inventoryQueryWrapper.eq("date", inventory.getDate());
            isDate = true;
        }
        if (inventory.getPurchasePrice() != null) {
            inventoryQueryWrapper.eq("purchasePrice", inventory.getPurchasePrice());
            isOther = true;
        }
        if (inventory.getSurplusQuantity() != null) {
            inventoryQueryWrapper.eq("id", inventory.getSurplusQuantity());
            isOther = true;
        }
        List<Map<String, Object>> inventories;
        if (isOther) {
            inventories = inventoryMapper.selectMaps(inventoryQueryWrapper);
        } else {
            inventories = selectAll();
        }
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> map : inventories) {
            boolean check = isDate &&
                inventory.getDate().getTime() == ((Date) map.get("date")).getTime();
            if (check == isDate) {
                res.add(map);
            }
        }
        return res;
    }

    public boolean deleteInventoryByAny(Inventory inventory) {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        boolean isDate = false;
        boolean isOther = false;
        if (inventory.getId() != null) {
            inventoryQueryWrapper.eq("id", inventory.getId());
            isOther = true;
        }
        if (inventory.getSupplyCenter() != null) {
            inventoryQueryWrapper.eq("supplyCenter", inventory.getSupplyCenter());
            isOther = true;
        }
        if (inventory.getProductModel() != null) {
            inventoryQueryWrapper.eq("ProductModel", inventory.getProductModel());
            isOther = true;
        }
        if (inventory.getSupplyStaff() != null) {
            inventoryQueryWrapper.eq("supplyStaff", inventory.getSupplyStaff());
            isOther = true;
        }
        if (inventory.getDate() != null) {
//            inventoryQueryWrapper.eq("date", inventory.getDate());
            isDate = true;
        }
        if (inventory.getPurchasePrice() != null) {
            inventoryQueryWrapper.eq("purchasePrice", inventory.getPurchasePrice());
            isOther = true;
        }
        if (inventory.getSurplusQuantity() != null) {
            inventoryQueryWrapper.eq("id", inventory.getSurplusQuantity());
            isOther = true;
        }
        List<Map<String, Object>> inventories;
        if (isOther) {
            inventories = inventoryMapper.selectMaps(inventoryQueryWrapper);
        } else {
            inventories = selectAll();
        }
        for (Map<String, Object> map : inventories) {
            boolean check = isDate &&
                inventory.getDate().getTime() == ((Date) map.get("date")).getTime();
            if (check == isDate) {
                inventoryMapper.deleteById((int) map.get("id"));
            }
        }
        return true;
    }
}
