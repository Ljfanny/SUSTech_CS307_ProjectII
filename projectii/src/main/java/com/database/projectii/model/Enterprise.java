package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

@Component
@TableName("enterprises")
//@ConfigurationProperties(prefix = "enterprise")
public class Enterprise {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String supplyCenter;
    private String industry;

    @Override
    public String toString() {
        return "Enterprise{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", supplyCenter='" + supplyCenter + '\'' +
            ", industry='" + industry + '\'' +
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
