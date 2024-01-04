package hu.neuron.mentoring.clientapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status",referencedColumnName = "id")
    private Status status;

    private String paymentOption;

    private String shippingOption;

    private String address;

    private Date orderDate;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL,orphanRemoval = true , fetch = FetchType.EAGER)
    private List<Shipment> orderedItems;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getShippingOption() {
        return shippingOption;
    }

    public void setShippingOption(String shippingOption) {
        this.shippingOption = shippingOption;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Shipment> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<Shipment> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
