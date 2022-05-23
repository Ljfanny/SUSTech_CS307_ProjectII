package com.database.projectii.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class ReturnOrder {
    private String contractNumber;
    private String contractManager;
    private String enterprise;
    private String supplyCenter;
    private String productModel;
    private String salesmanNumber;
    private Integer quantity;
    private Integer unitPrice;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private java.sql.Date estimatedDeliveryDate;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private java.sql.Date lodgementDate;

    public ReturnOrder(String contractNumber, String contractManager, String enterprise,
                       String supplyCenter, String productModel, String salesmanNumber,
                       Integer quantity, Integer unitPrice, Date estimatedDeliveryDate,
                       Date lodgementDate) {
        this.contractNumber = contractNumber;
        this.contractManager = contractManager;
        this.enterprise = enterprise;
        this.supplyCenter = supplyCenter;
        this.productModel = productModel;
        this.salesmanNumber = salesmanNumber;
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

    public String getContractManager() {
        return contractManager;
    }

    public void setContractManager(String contractManager) {
        this.contractManager = contractManager;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
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

    public String getSalesmanNumber() {
        return salesmanNumber;
    }

    public void setSalesmanNumber(String salesmanNumber) {
        this.salesmanNumber = salesmanNumber;
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

    public java.sql.Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(java.sql.Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public java.sql.Date getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(java.sql.Date lodgementDate) {
        this.lodgementDate = lodgementDate;
    }
}