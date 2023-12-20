package hu.neuron.mentoring.clientapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category",referencedColumnName = "id")
    private Category category;

    private Integer amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "unit",referencedColumnName = "id")
    private Unit unit;

    private BigDecimal purchasePrice;

    private BigDecimal sellPrice;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Stock> pastData = new ArrayList<>();

    public Product(String name, Category category, Integer amount, Unit unit, BigDecimal purchasePrice, BigDecimal sellPrice, String description) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.unit = unit;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.description = description;
    }

    public Product(String name, Integer amount, BigDecimal purchasePrice, BigDecimal sellPrice, String description) {
        this.name = name;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.description = description;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
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
