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
@TableName("empty_contracts")
public class Contract {

    private String number;
    private String manager;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date date;
    private String enterprise;
    private String center;

    public Contract(){}

    @Override
    public String toString() {
        return "Contract{" +
            "number='" + number + '\'' +
            ", manager='" + manager + '\'' +
            ", date=" + date +
            ", enterprise='" + enterprise + '\'' +
            ", center='" + center + '\'' +
            '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
