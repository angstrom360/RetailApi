package com.trilogy.retailstore.controller;

import com.trilogy.retailstore.model.Invoice;
import com.trilogy.retailstore.model.Product;
import com.trilogy.retailstore.serviceLayer.RetailServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameStoreController {

    // Retail Endpoints

    @Autowired
    RetailServiceLayer service;

    /**Can do!*/
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public String submitInvoice(@RequestBody Invoice invoice) {
        return service.submitInvoice(invoice);
    }

    /**Can do!*/
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public Invoice getInvoiceById(@PathVariable int id) {
        return service.getInvoiceById(id);
    }

    /**Can do!*/
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> getAllInvoices() {
        return service.getAllInvoices();
    }

    /**Can do!*/
    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) {
        return service.getInvoiceByCustomerId(id);
    }

    /**Can do! -- However is not specifying which inventory (Return all Products). */
    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<Product> getProductsInInventory() {
        return service.getProductsInInventory();
    }

    /**Can do!*/
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return service.getProductsById(id);
    }

    /**Can do! -- Will need to implement a API inside another api inside a view model.
     * 1. Need API from Product API: getProductsByInventoryId*/
    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return service.getProductByInvoiceId(id);
    }

    /**Can do!*/
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable int id) {
        return service.getLevelUpPointsByCustomerId(id);
    }

    // Admin Endpoints

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product) {
        return null;
    }

    // getProductById is above in the Retail endpoints

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return null;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {

    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id) {

    }

}
