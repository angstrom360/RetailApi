package com.trilogy.retailstore.feign;

import com.trilogy.retailstore.model.Invoice;
import com.trilogy.retailstore.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="invoice")
public interface InvoiceApi {

    @RequestMapping(value = "/retailInvoice",method= RequestMethod.POST)
    Invoice createInvoice(@RequestBody @Valid Invoice invoice);

    @RequestMapping(value ="/retailInvoice/{id}", method= RequestMethod.GET)
    Invoice getInvoices(@PathVariable int id);

    @RequestMapping(value = "/retailInvoice/all", method = RequestMethod.GET)
    List<Invoice> getAllInvoices();

    @RequestMapping(value="/retailInvoice/customer/{id}",method=RequestMethod.GET)
    List<Invoice> getInvoiceByCustomerId(@PathVariable @Valid int id);

    @RequestMapping(value ="/invoiceItem/invoice/{id}",method=RequestMethod.GET)
    List<InvoiceItem> getInvoiceItemByInvoiceId(@PathVariable @Valid int id);

}
