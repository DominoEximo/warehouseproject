package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Order;
import hu.neuron.mentoring.clientapi.entity.Shipment;
import hu.neuron.mentoring.clientapi.service.OrderService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.StatusService;
import hu.neuron.mentoring.clientapi.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class OrderBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(OrderBean.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    StatusService statusService;

    private Order currentOrder;

    private Integer orderQuantity;

    private Double overallPrice;

    private String addressPostalCode;
    private String addressCountry;
    private String addressStreet;

    @PostConstruct
    public void init(){
        try {
            statusService.setUpMockedData();
            currentOrder = new Order();
            currentOrder.setOrderedItems(new ArrayList<>());
            overallPrice = 0.0;
            orderQuantity = 0;
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }
    }


    public void addOfferToOrder(Offer offer){
        String transactionName = "addOfferToOrder";
        if (orderQuantity > 0 && orderQuantity <= offer.getProduct().getAmount()){
            try {
                logger.info("Transaction '{}' started in OrderBean", transactionName);
                Shipment shipment = new Shipment();
                shipment.setOffer(offer);
                shipment.setQuantity(orderQuantity);
                productService.updateProductQuantity(offer.getProduct().getId(),offer.getProduct().getAmount() - orderQuantity);
                List<Long> offers = currentOrder.getOrderedItems().stream().map(x -> x.getOffer().getId()).collect(Collectors.toList());
                if (offers.contains(offer.getId())){
                    for (Shipment shipment1: currentOrder.getOrderedItems()){
                        if (shipment1.getOffer().getId().equals(offer.getId())){
                            shipment1.setQuantity(shipment1.getQuantity() + orderQuantity);
                            break;
                        }
                    }
                }else{
                    currentOrder.getOrderedItems().add(shipment);
                }

                overallPrice = overallPrice + orderQuantity * offer.getPrice().intValue();
                logger.info("Transaction '{}' completed successfully in OrderBean", transactionName);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Item successfully added to cart");
                PrimeFaces.current().dialog().showMessageDynamic(message);

            }catch (Exception e){
                logger.error("Transaction '{}' failed in OrderBean: {}",transactionName,e.getMessage());

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error during adding item to cart");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }else{

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Invalid quantity of items");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
        orderQuantity = 0;
    }

    public void removeOffer(Offer offer, int amountToReturn){
        String transactionName = "deleteOfferFromCart";
        try {
            logger.info("Transaction '{}' started in OrderBean", transactionName);

            productService.updateProductQuantity(offer.getProduct().getId(), productService.getproductById(offer.getProduct().getId()).getAmount() + amountToReturn);

            currentOrder.getOrderedItems().removeIf(shipment -> shipment.getOffer().getId().equals(offer.getId()));

            overallPrice = overallPrice - (amountToReturn * offer.getPrice());


            logger.info("Transaction '{}' completed successfully in OrderBean", transactionName);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Item successfully removed from cart");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }catch (Exception e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error while deleting offer");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }

    }

    public void storeOrder(){
        String transactionName = "order";
        try {
            logger.info("Transaction '{}' started in OrderBean", transactionName);

            currentOrder.setUser(userService.findByName(getUsername()));

            if (currentOrder.getShippingOption().equals("shipping")){

                currentOrder.setAddress(addressPostalCode + " " + addressCountry + " " + addressStreet);

            }
            else{
                currentOrder.setAddress("");
            }

            currentOrder.setStatus(statusService.findByName("Pending"));

            orderService.save(currentOrder);

            flushOrder();

            logger.info("Transaction '{}' completed successfully in OrderBean", transactionName);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Successfully ordered");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }catch (Exception e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error while processing order");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
    }

    public String getUsername() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpSession session = request.getSession(false);

        if (session != null) {
            return request.getUserPrincipal().getName();
        }

        return null;
    }

    public void flushOrder(){
        currentOrder = new Order();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Double getOverallPrice() {
        return overallPrice;
    }

    public void setOverallPrice(Double overallPrice) {
        this.overallPrice = overallPrice;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
}
