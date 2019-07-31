package com.trilogy.retailstore.feign;

import com.trilogy.retailstore.model.LevelUp;
import com.trilogy.retailstore.serviceLayer.RetailServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Component
public class LevelUpFallBack implements LevelUpApi {

    @Autowired
    RetailServiceLayer serviceLayer;


    @Override
    public LevelUp getLevelUpByCustomerId(@Valid int id) {
        return null;
    }

    @Override
    public LevelUp createLevelUp(@Valid LevelUp levelUp) {
        return null;
    }

    @Override
    public void updateLevelUps(@Valid LevelUp levelUp, int id) {

    }

    @Override
    public String getPointsByCustomerId(@PathVariable @Valid int id){
        return "Sorry we are having issues recovering the total points from the Service. ";
    }




}
