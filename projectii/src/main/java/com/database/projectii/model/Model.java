package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

@Component
@TableName("models")
public class Model {
    private Integer id;
    private String number;
    private String model;
    private String name;
    private Integer unitPrice;

    @Override
    public String toString() {
        return "Model{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", model='" + model + '\'' +
            ", name='" + name + '\'' +
            ", unitPrice=" + unitPrice +
            '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}
