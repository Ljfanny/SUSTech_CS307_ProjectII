package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

@Component
@TableName("staffs")
public class Staff {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private String number;
    private String supplyCenter;
    private String mobileNumber;
    private String type;

    @Override
    public String toString() {
        return "Staff{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", gender='" + gender + '\'' +
            ", number='" + number + '\'' +
            ", supplyCenter='" + supplyCenter + '\'' +
            ", mobileNumber='" + mobileNumber + '\'' +
            ", type='" + type + '\'' +
            '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
