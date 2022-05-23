package com.database.projectii.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.database.projectii.model.Inventory;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    @Select("select count(*) as cnt " +
        "from (select distinct product_model from inventories except (select distinct product_model from orders))sub")
    Object selectNeverSoldProductCount();


    @Select(
        "select supply_center, number as product_number, product_model, purchase_price, i.surplus_quantity " +
            "from models join inventories i on models.model = i.product_model " +
            "where number = #{productNumber}")
    List<Map<String, Object>> selectProductByNumber(@Param("productNumber") String productNumber);

}
