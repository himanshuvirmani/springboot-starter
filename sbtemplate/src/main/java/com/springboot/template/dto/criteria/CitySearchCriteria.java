package com.springboot.template.dto.criteria;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CitySearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public CitySearchCriteria(String name) {
        Assert.notNull(name, "Name must not be null");
        this.name = name;
    }
}
