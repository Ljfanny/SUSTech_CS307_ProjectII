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
import com.database.projectii.model.Staff;
import com.database.projectii.service.InventoryService;
import com.database.projectii.service.property.StaffType;
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
        inventory.setSurplusQuantity(inventory.getTotalQuantity());
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
}
