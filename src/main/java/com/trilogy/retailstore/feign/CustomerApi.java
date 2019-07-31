package com.trilogy.retailstore.feign;

import com.trilogy.retailstore.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="customer")
public interface CustomerApi {
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    Customer createCustomers(@RequestBody @Valid Customer customerViewModel);

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    Customer getCustomers(@PathVariable int id);

    @RequestMapping(value = "/customers/all", method = RequestMethod.GET)
    List<Customer> getAllCustomer();

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    void deletePost(@PathVariable int id);


}
