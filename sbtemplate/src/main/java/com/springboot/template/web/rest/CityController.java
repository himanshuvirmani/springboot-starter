package com.springboot.template.web.rest;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.springboot.template.domain.City;
import com.springboot.template.exception.Errors;
import com.springboot.template.service.api.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * Created by himanshu.virmani
 */
@RestController
@RequestMapping("/city")
@Slf4j
@Api(value = "/", description = "Serving APIs")
public class CityController {

    @Autowired
    private CityService cityService;

    @Timed
    @Metered(name = "get city", absolute = true)
    @ApiOperation(httpMethod = "GET", value = "Get City", response = City.class)
    @RequestMapping(value = "/{city}/country/{country}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity getCity(@PathVariable("city") String city, @PathVariable("country") String country) {
        City result = this.cityService.getCity(city, country);
        ResponseEntity responseEntity = null;
        if (result == null) {
            return responseEntity = new ResponseEntity<>(Errors.UNKNOWN_ERROR.getDescription(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        return responseEntity;
    }

}
