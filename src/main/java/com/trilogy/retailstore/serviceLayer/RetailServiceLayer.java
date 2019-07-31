package com.trilogy.retailstore.serviceLayer;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogy.retailstore.feign.*;
import com.trilogy.retailstore.model.*;
import com.trilogy.retailstore.viewmodel.InventoryViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class RetailServiceLayer {

    /*----------------------------- Importing RabbitTemplate Variables and keys ----------------*/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE = "levelUp-exchange";
    public static final String ROUTING_KEY = "levelUp.create.#";

    /*------------------------------------------------------------------------------------*/


    /*----------- Importing all objects and variables that will be used to build the ServiceLayer ----------------- */

    @Autowired
    CustomerApi customerApi;
    @Autowired
    InvoiceApi invoiceApi;
    @Autowired
    ProductApi productApi;

    LevelUpApi levelUpApi;
    @Autowired
    InventoryApi inventoryApi;


    public int totalPoints;

    public RetailServiceLayer(RabbitTemplate rabbitTemplate,CustomerApi customerApi, InvoiceApi invoiceApi, ProductApi productApi, LevelUpApi levelUpApi, InventoryApi inventoryApi) {
        this.rabbitTemplate = rabbitTemplate;
        this.customerApi = customerApi;
        this.invoiceApi = invoiceApi;
        this.productApi = productApi;
        this.levelUpApi = levelUpApi;
        this.inventoryApi = inventoryApi;
    }

    private InventoryViewModel buildInventoryViewModel (Inventory inventory)
    {
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(inventory.getInventoryId());
        inventoryViewModel.setQuantity(inventory.getQuantity());
       /* inventory = inventoryApi.createInventory(inventory);*/

        Product product = productApi.getProducts(inventory.getProductId());
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        inventoryViewModel.setProducts(productList);

        return inventoryViewModel;
    }
    /*--------------------------------------------------------------------------------------------------------*/

    /*=============================== TWITTER SERVICE BUSINESS LOGIC METHODS =================================*/



    public String submitInvoice(Invoice invoice){

        invoice = invoiceApi.createInvoice(invoice);
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();

        LevelUp levelUp = levelUpApi.getLevelUpByCustomerId(invoice.getCustomerId());

        BigDecimal fortyNine = BigDecimal.valueOf(49.00);
        BigDecimal fifty = BigDecimal.valueOf(50.00);
        BigDecimal oneHundredNine = BigDecimal.valueOf(109.00);
        BigDecimal hundredTen = BigDecimal.valueOf(110.00);
        String reply = null;

        for(InvoiceItem items: invoiceItems){
            if(items.getUnitPrice().compareTo(fortyNine) <= 0){
                levelUp.setPoints(levelUp.getPoints());
                totalPoints = levelUp.getPoints();
                 String pointResult = levelUpApi.getPointsByCustomerId(levelUp.getCustomerId());
                reply = "Your Invoice was successfully submitted! You have been awarded 0 Points. " + pointResult;
            }else if((items.getUnitPrice().compareTo(fifty) >= 0) && (items.getUnitPrice().compareTo(oneHundredNine) <= 0)){
                levelUp.setPoints(levelUp.getPoints()+10);
                totalPoints = levelUp.getPoints();
                rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,levelUp);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String pointResult = levelUpApi.getPointsByCustomerId(levelUp.getCustomerId());
                 reply = "Your Invoice was successfully submitted! You have been awarded 10 Points. " + pointResult;
            }else if(items.getUnitPrice().compareTo(hundredTen) >= 0){
                levelUp.setPoints(levelUp.getPoints()+20);
                totalPoints = levelUp.getPoints();
                rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,levelUp);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String pointResult = levelUpApi.getPointsByCustomerId(levelUp.getCustomerId());
                 reply = "Your Invoice was successfully submitted! You have been awarded 20 Points. " + pointResult;
            }else{
                 break;
            }

        }return reply;

    }

    public Invoice getInvoiceById(int id){
        return invoiceApi.getInvoices(id);
    }

    public List<Invoice> getAllInvoices(){
        return invoiceApi.getAllInvoices();
    }

    public List<Invoice> getInvoiceByCustomerId(int id){
        return invoiceApi.getInvoiceByCustomerId(id);
    }

    public List<Product> getProductsInInventory(){
        return productApi.getAllProduct();
    }

    public Product getProductsById(int id){
        return productApi.getProducts(id);
    }

    public List<Product> getProductByInvoiceId(int id){

        List<InvoiceItem> invoiceItemList = invoiceApi.getInvoiceItemByInvoiceId(id);

        List<InventoryViewModel> inventoryViewModelList = new ArrayList<>();

        for(InvoiceItem invoiceItem1: invoiceItemList){
             InventoryViewModel ivm = buildInventoryViewModel(inventoryApi.getInventorys(
                     invoiceItem1.getInventoryId()));
            inventoryViewModelList.add(ivm);
        }
        List<Product> productList = new ArrayList<>();

        for(InventoryViewModel inv2 :inventoryViewModelList ){
            productList.addAll(inv2.getProducts());
        }
        return productList;
    }

    public int getLevelUpPointsByCustomerId(int id){

        LevelUp levelUps = levelUpApi.getLevelUpByCustomerId(id);

        return levelUps.getPoints();
    }


}
