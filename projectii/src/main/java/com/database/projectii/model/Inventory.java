package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@TableName("inventories")
public class Inventory {
    private Integer id;
    private String supplyCenter;
    private String productModel;
    private String supplyStaff;
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date date;
    private Integer purchasePrice;
    private Integer surplusQuantity;
    private Integer totalQuantity;

    public Inventory(){}

    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + id +
            ", supplyCenter='" + supplyCenter + '\'' +
            ", productModel='" + productModel + '\'' +
            ", supplyStaff='" + supplyStaff + '\'' +
            ", date=" + date +
            ", purchasePrice=" + purchasePrice +
            ", quantitySurplus=" + surplusQuantity +
            ", quantityTotal=" + totalQuantity +
            '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getSupplyStaff() {
        return supplyStaff;
    }

    public void setSupplyStaff(String supplyStaff) {
        this.supplyStaff = supplyStaff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getSurplusQuantity() {
        return surplusQuantity;
    }

    public void setSurplusQuantity(Integer surplusQuantity) {
        this.surplusQuantity = surplusQuantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
