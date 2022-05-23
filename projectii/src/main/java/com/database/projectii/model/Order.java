package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@TableName("orders")
public class Order implements Comparable<Order> {
    private String contractNumber;
    private String enterprise;
    private String productModel;
    private Integer quantity;
    private String contractManager;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date contractDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date estimatedDeliveryDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date lodgementDate;
    private String salesmanNumber;
    private String contractType;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
            "contractNumber='" + contractNumber + '\'' +
            ", enterprise='" + enterprise + '\'' +
            ", productModel='" + productModel + '\'' +
            ", quantity=" + quantity +
            ", contractManager='" + contractManager + '\'' +
            ", contractDate=" + contractDate +
            ", EstimatedDeliveryDate=" + estimatedDeliveryDate +
            ", lodgementDate=" + lodgementDate +
            ", salesmanNumber='" + salesmanNumber + '\'' +
            ", contractType='" + contractType + '\'' +
            '}';
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getContractManager() {
        return contractManager;
    }

    public void setContractManager(String contractManager) {
        this.contractManager = contractManager;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(Date lodgementDate) {
        this.lodgementDate = lodgementDate;
    }

    public String getSalesmanNumber() {
        return salesmanNumber;
    }

    public void setSalesmanNumber(String salesmanNumber) {
        this.salesmanNumber = salesmanNumber;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    @Override
    public int compareTo(Order o) {
        int result = this.estimatedDeliveryDate.compareTo(o.getEstimatedDeliveryDate());
        if (result == 0) {
            return this.getProductModel().compareTo(o.productModel);
        } else {
            return result;
        }
    }
}
