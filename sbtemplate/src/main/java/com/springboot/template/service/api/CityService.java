package com.springboot.template.service.api;

import com.springboot.template.domain.City;
import com.springboot.template.dto.criteria.CitySearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    Page<City> findCities(CitySearchCriteria criteria, Pageable pageable);

    City getCity(String name, String country);

}
