package com.trilogy.retailstore.servicelayer;

import com.trilogy.retailstore.feign.*;
import com.trilogy.retailstore.model.*;
import com.trilogy.retailstore.serviceLayer.RetailServiceLayer;
import com.trilogy.retailstore.viewmodel.InventoryViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetailServiceLayerTest {

    @Autowired
    RetailServiceLayer serviceLayer;

    @Mock
    CustomerApi customerApi;
    @Mock
    InvoiceApi invoiceApi;
    @Mock
    ProductApi productApi;
    @Mock
    LevelUpApi levelUpApi;
    @Mock
    InventoryApi inventoryApi;
    @Mock
    RabbitTemplate rabbitTemplate;

    private void setUpCustomerServer(){
        customerApi=mock(CustomerApi.class);
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Ryan");
        customer.setLastName("Howard");
        customer.setStreet("6892 N. Clark Street");
        customer.setCity("Houston");
        customer.setZip("123456");
        customer.setEmail("ryanHoward@aol.com");
        customer.setPhone("915-874-0001");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Ryan");
        customer1.setLastName("Howard");
        customer1.setStreet("6892 N. Clark Street");
        customer1.setCity("Houston");
        customer1.setZip("123456");
        customer1.setEmail("ryanHoward@aol.com");
        customer1.setPhone("915-874-0001");

        List<Customer> cList = new ArrayList<>();
        cList.add(customer);

        when(customerApi.createCustomers(customer)).thenReturn(customer);

    }

    private void setUpInvoiceServer(){
        invoiceApi=mock(InvoiceApi.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceItem.getInvoiceId());
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(iList);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(10);
        invoiceItem1.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList1 = new ArrayList<>();
        iList1.add(invoiceItem1);


        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setInvoiceItems(iList1);
        invoice1.setPurchaseDate(LocalDate.of(2020,12,12));

        List<Invoice> cList = new ArrayList<>();
        cList.add(invoice);

        when(invoiceApi.createInvoice(invoice)).thenReturn(invoice);
        when(invoiceApi.getInvoices(1)).thenReturn(invoice);
        when(invoiceApi.getAllInvoices()).thenReturn(cList);
        when(invoiceApi.getInvoiceByCustomerId(1)).thenReturn(cList);
        when(invoiceApi.getInvoiceItemByInvoiceId(1)).thenReturn(iList);


    }

    private void setUpProductServer(){
        productApi=mock(ProductApi.class);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("PS4");
        product.setProductDescription("Game");
        product.setListPrice(new BigDecimal(12));
        product.setUnitCost(new BigDecimal(15));

        Product product1 = new Product();
        product1.setProductName("PS4");
        product1.setProductDescription("Game");
        product1.setListPrice(new BigDecimal(12));
        product1.setUnitCost(new BigDecimal(15));

        List<Product> pList = new ArrayList<>();
        pList.add(product);

        when(productApi.getProducts(product)).thenReturn(product);
        when(productApi.getProducts(1)).thenReturn(product);
        when(productApi.getAllProduct()).thenReturn(pList);

    }

    private void setUpLevelUpServer(){
        levelUpApi=mock(LevelUpApi.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,07,28));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(2019,07,28));

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setCustomerId(2);
        levelUp2.setPoints(15);
        levelUp2.setMemberDate(LocalDate.of(2019,07,28));

        List<LevelUp> pList = new ArrayList<>();
        pList.add(levelUp);


        when(levelUpApi.getLevelUpByCustomerId(1)).thenReturn(levelUp);
        when(levelUpApi.createLevelUp(levelUp)).thenReturn(levelUp);
    }

    private void SetUpInventoryServer(){
        inventoryApi=mock(InventoryApi.class);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        List<Inventory> iList = new ArrayList<>();
        iList.add(inventory);

        when(inventoryApi.getInventorys(1)).thenReturn(inventory);
        when(inventoryApi.createInventory(inventory)).thenReturn(inventory);

    }

    @Before
    public void SetUp() throws Exception{
        setUpCustomerServer();
        setUpInvoiceServer();
        setUpLevelUpServer();
        setUpProductServer();
        SetUpInventoryServer();

        serviceLayer = new RetailServiceLayer(rabbitTemplate,customerApi,invoiceApi,productApi,levelUpApi,inventoryApi);
    }

    @Test
    public void saveInvoice(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(iList);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));
        invoice.setInvoiceItems(iList);
        invoice = invoiceApi.createInvoice(invoice);

        String what = serviceLayer.submitInvoice(invoice);

        Invoice fromService = serviceLayer.getInvoiceById(1);
        String stringFromSerivce = serviceLayer.submitInvoice(fromService);

        assertEquals(what,stringFromSerivce);
    }

    @Test
    public void getAllInvoices(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(iList);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));
        invoice.setInvoiceItems(iList);
        invoice = invoiceApi.createInvoice(invoice);

        List<Invoice> fromService = serviceLayer.getAllInvoices();
        assertEquals(1,fromService.size());
        assertEquals(invoice,fromService.get(0));
    }

    @Test
    public void getInvoiceByCustomerId(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(iList);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));
        invoice.setInvoiceItems(iList);
        invoice = invoiceApi.createInvoice(invoice);

        List<Invoice> fromService = serviceLayer.getInvoiceByCustomerId(invoice.getCustomerId());
        assertEquals(1,fromService.size());
        assertEquals(invoice,fromService.get(0));
    }

    @Test
    public void getProductsInInventory(){

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("PS4");
        product.setProductDescription("Game");
        product.setListPrice(new BigDecimal(12));
        product.setUnitCost(new BigDecimal(15));
        product = productApi.getProducts(product);

        Product fromService = serviceLayer.getProductsById(1);
        assertEquals(product,fromService);

        List<Product> pList = serviceLayer.getProductsInInventory();
        assertEquals(1,pList.size());
        assertEquals(product,pList.get(0));
    }
    @Test
    public void getProductByInvoiceId(){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal(12));
        List<InvoiceItem> iList = new ArrayList<>();
        iList.add(invoiceItem);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(iList);
        invoice.setPurchaseDate(LocalDate.of(2020,12,12));
        invoice.setInvoiceItems(iList);
        invoice = invoiceApi.createInvoice(invoice);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("PS4");
        product.setProductDescription("Game");
        product.setListPrice(new BigDecimal(12));
        product.setUnitCost(new BigDecimal(15));
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        product = productApi.getProducts(product);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryApi.createInventory(inventory);
        inventory = inventoryApi.getInventorys(inventory.getInventoryId());

        InventoryViewModel ivm = new InventoryViewModel();
        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setProducts(productList);
        ivm.setQuantity(inventory.getQuantity());

        List<Product> pList = ivm.getProducts();

        List<Product> fromService = serviceLayer.getProductByInvoiceId(invoice.getInvoiceId());


        assertEquals(1,fromService.size());
        assertEquals(pList,fromService);
    }

    @Test
    public void getLevelUpPointsByCustomerId(){

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Ryan");
        customer.setLastName("Howard");
        customer.setStreet("6892 N. Clark Street");
        customer.setCity("Houston");
        customer.setZip("123456");
        customer.setEmail("ryanHoward@aol.com");
        customer.setPhone("915-874-0001");
        customer = customerApi.createCustomers(customer);

        LevelUp levelUp = new LevelUp();
        levelUp.setId(1);
        levelUp.setCustomerId(customer.getCustomerId());
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,07,28));
        levelUp = levelUpApi.createLevelUp(levelUp);

        int points = levelUp.getPoints();

        int fromService = serviceLayer.getLevelUpPointsByCustomerId(levelUp.getCustomerId());

        assertEquals(points,fromService);
    }












}
