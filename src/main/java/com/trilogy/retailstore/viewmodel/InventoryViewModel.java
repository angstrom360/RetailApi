package com.trilogy.retailstore.viewmodel;

import com.trilogy.retailstore.model.Product;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class InventoryViewModel {

    private int inventoryId;
    @NotNull(message = "Please enter the list of Products.")
    private List<Product> products;
    @NotNull(message = "Please enter a quantity.")
    private int quantity;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryViewModel that = (InventoryViewModel) o;
        return inventoryId == that.inventoryId &&
                quantity == that.quantity &&
                products.equals(that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, products, quantity);
    }
}
