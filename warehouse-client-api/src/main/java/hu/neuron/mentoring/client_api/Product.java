package hu.neuron.mentoring.client_api;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private String name;

    private String category;

    private Integer amount;
    private String unit;

    private BigDecimal purchasePrice;

    private BigDecimal sellPrice;

    private String description;


    public Product(String name, String category, Integer amount, String unit, BigDecimal purchasePrice, BigDecimal sellPrice, String description) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.unit = unit;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.description = description;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(category, product.category) && Objects.equals(amount, product.amount) && Objects.equals(unit, product.unit) && Objects.equals(purchasePrice, product.purchasePrice) && Objects.equals(sellPrice, product.sellPrice) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, amount, unit, purchasePrice, sellPrice, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", description='" + description + '\'' +
                '}';
    }
}
