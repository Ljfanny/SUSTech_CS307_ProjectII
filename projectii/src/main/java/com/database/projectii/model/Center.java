package com.database.projectii.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@TableName("center_view")
public class Center {
    private Integer id;
    private String name;

    public Center(){}

    @Override
    public String toString() {
        return "Center{" +
            "id=" + id +
            ", name='" + name + '\'' +
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
}
