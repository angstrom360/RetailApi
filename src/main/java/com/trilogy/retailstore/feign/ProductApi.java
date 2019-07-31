package com.trilogy.retailstore.feign;

import com.trilogy.retailstore.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="product")
public interface ProductApi {

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    Product getProducts(@RequestBody Product product);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    Product getProducts(@PathVariable int id);

    @RequestMapping(value = "/products/all", method = RequestMethod.GET)
    List<Product> getAllProduct();


}
