package com.database.projectii.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class ReturnOrder {
    private String contractNumber;
    private String contractManagerName;
    private String enterprise;
    private String supplyCenter;
    private String productModel;
    private String salesmanName;
    private Integer quantity;
    private Integer unitPrice;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private java.sql.Date estimatedDeliveryDate;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private java.sql.Date lodgementDate;

    public ReturnOrder(String contractNumber, String contractManagerName,
                       String enterpriseName, String supplyCenter, String productModel,
                       String salesmanName, Integer quantity, Integer unitPrice,
                       Date estimatedDeliveryDate, Date lodgementDate) {
        this.contractNumber = contractNumber;
        this.contractManagerName = contractManagerName;
        this.enterprise = enterpriseName;
        this.supplyCenter = supplyCenter;
        this.productModel = productModel;
        this.salesmanName = salesmanName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.lodgementDate = lodgementDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractManagerName() {
        return contractManagerName;
    }

    public void setContractManagerName(String contractManagerName) {
        this.contractManagerName = contractManagerName;
    }

    public String getEnterpriseName() {
        return enterprise;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterprise = enterpriseName;
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

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
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
}