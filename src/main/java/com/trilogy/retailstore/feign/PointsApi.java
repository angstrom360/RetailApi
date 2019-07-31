/*
package com.trilogy.retailstore.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "levelUp", fallback = LevelUpFallBack.class)
public interface PointsApi  {

    @RequestMapping(value = "/levelUp/points/customer/{id}",method = RequestMethod.GET)
    String getPointsByCustomerId(@PathVariable @Valid int id);
}
*/
