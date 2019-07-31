package com.trilogy.retailstore.feign;

import com.trilogy.retailstore.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="inventory")
public interface InventoryApi {

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    Inventory getInventorys(@PathVariable int id);

    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    Inventory createInventory(@RequestBody Inventory inventory);
}
