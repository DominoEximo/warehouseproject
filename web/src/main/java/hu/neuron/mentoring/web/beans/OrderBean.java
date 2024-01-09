package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.*;
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
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class OrderBean implements Serializable {

    private static final String TRANSACTION_STARTED_MESSAGE = "Transaction '{}' started in OrderBean";

    private static final String TRANSACTION_SUCCESS_MESSAGE = "Transaction '{}' completed successfully in OrderBean";

    private static final String TRANSACTION_FAILED_MESSAGE = "Transaction '{}' failed in OrderBean";

    private static final String SUCCESS_MESSAGE = "Success";

    private static final String ERROR_MESSAGE = "Error";

    private static final Logger logger = LogManager.getLogger(OrderBean.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    StatusService statusService;

    private OrderItem currentOrderItem;

    private OrderItem orderToBeManaged;

    private List<String> orderToBeManagedAddress;

    private List<OrderItem> orders;

    private Integer orderQuantity;

    private Double overallPrice;

    private String addressPostalCode;
    private String addressCountry;
    private String addressStreet;

    private int page = 1;

    private int length = 5;

    private Map<Long,String> statuses;

    private Long chosenStatus;

    private String selectedStatus;

    private List<String> statusNames;

    @PostConstruct
    public void init(){
        try {
            statusService.setUpMockedData();
            currentOrderItem = new OrderItem();
            statuses = new LinkedHashMap<>();
            currentOrderItem.setOrderedItems(new ArrayList<>());
            orderToBeManaged = new OrderItem();
            orderToBeManaged.setOrderedItems(new ArrayList<>());
            orderToBeManagedAddress = new ArrayList<>();
            overallPrice = 0.0;
            orderQuantity = 0;
            statusNames = statusService.findAll().stream().map(Status::getStatusName).collect(Collectors.toList());
            getStatusList();
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }
    }


    public void addOfferToOrder(Offer offer){
        String transactionName = "addOfferToOrder";
        if (offer.getQuantity() > 0 && offer.getQuantity() <= offer.getProduct().getAmount()){
            try {
                logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
                Shipment shipment = new Shipment();
                shipment.setOffer(offer);
                shipment.setQuantity(offer.getQuantity());
                productService.updateProductQuantity(offer.getProduct().getId(),offer.getProduct().getAmount() - offer.getQuantity());
                List<Long> offers = currentOrderItem.getOrderedItems().stream().map(x -> x.getOffer().getId()).collect(Collectors.toList());
                if (offers.contains(offer.getId())){
                    for (Shipment shipment1: currentOrderItem.getOrderedItems()){
                        if (shipment1.getOffer().getId().equals(offer.getId())){
                            shipment1.setQuantity(shipment1.getQuantity() + offer.getQuantity());
                            break;
                        }
                    }
                }else{
                    currentOrderItem.getOrderedItems().add(shipment);
                    shipment.setOrderItem(currentOrderItem);
                }

                overallPrice = overallPrice + offer.getQuantity() * offer.getPrice().intValue();
                logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_MESSAGE, "Item successfully added to cart");
                PrimeFaces.current().dialog().showMessageDynamic(message);

            }catch (Exception e){
                logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ERROR_MESSAGE, "Error during adding item to cart");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }
        }else{

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ERROR_MESSAGE, "Invalid quantity of items");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
        offer.setQuantity(0);
    }

    public void removeOffer(Offer offer, int amountToReturn){
        String transactionName = "deleteOfferFromCart";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));

            productService.updateProductQuantity(offer.getProduct().getId(), productService.getproductById(offer.getProduct().getId()).getAmount() + amountToReturn);

            currentOrderItem.getOrderedItems().removeIf(shipment -> shipment.getOffer().getId().equals(offer.getId()));

            overallPrice = overallPrice - (amountToReturn * offer.getPrice());


            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_MESSAGE, "Item successfully removed from cart");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ERROR_MESSAGE, "Error while deleting offer");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }

    }

    public void storeOrder(){
        String transactionName = "order";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));

            currentOrderItem.setUser(userService.findByName(getUsername()));

            if (currentOrderItem.getShippingOption().equals("shipping")){

                currentOrderItem.setAddress(addressPostalCode + " " + addressCountry + " " + addressStreet);

            }
            else{
                currentOrderItem.setAddress("");
            }

            currentOrderItem.setStatus(statusService.findByName("Pending"));

            currentOrderItem.setOrderDate(new Date());

            orderService.save(currentOrderItem);

            flushOrder();

            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_MESSAGE, "Successfully ordered");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ERROR_MESSAGE, "Error while processing order");
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

    public void loadOrders(){
        String transactionName = "loadOrders";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
            orders = null;
            orders = orderService.findAllPaginated(page,length);
            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
        }
    }

    public void loadFilteredOrders(){
        String transactionName = "filterOffersByStatus";
        try {
            if(selectedStatus == null || selectedStatus.isEmpty()){
                loadOrders();
            }
            else {
                logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
                orders = null;
                orders = orderService.findAllByStatus(selectedStatus);
                logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
            }

        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
        }
    }


    public void getStatusList(){
        statuses.clear();
        for (Status status : statusService.findAll()){
            statuses.put(status.getId(),status.getStatusName());
        }
    }

    public void nextPage(){
        if(orderService.findAllPaginated(page,length).size() / length >= page){
            page += 1;
            loadOrders();
        }
    }
    public void prevPage(){
        if(page > 1){
            page -= 1;
            loadOrders();
        }

    }

    public void setUpOrderToBeManaged(OrderItem order){
        orderToBeManaged.setId(order.getId());
        orderToBeManaged.setUser(order.getUser());
        orderToBeManaged.setAddress(order.getAddress());
        orderToBeManaged.setOrderDate(order.getOrderDate());
        orderToBeManaged.setStatus(order.getStatus());
        orderToBeManaged.setPaymentOption(order.getPaymentOption());
        orderToBeManaged.setOrderedItems(order.getOrderedItems());
        orderToBeManaged.setShippingOption(order.getShippingOption());
        orderToBeManagedAddress = List.of(orderToBeManaged.getAddress().split(" "));

    }

    public void editOrder(){
        String transactionName = "editOrder";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
            orderToBeManaged.setStatus(statusService.findById(chosenStatus));
            orderService.save(orderToBeManaged);
            clearOrderToBeManaged();
            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_MESSAGE, "Successfully edited order");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ERROR_MESSAGE, "Error while editing order");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
    }

    public void clearOrderToBeManaged(){
        orderToBeManaged = new OrderItem();
        orderToBeManaged.setOrderedItems(new ArrayList<>());
        orderToBeManagedAddress = new ArrayList<>();
    }

    public void flushOrder(){
        currentOrderItem = new OrderItem();
        currentOrderItem.setOrderedItems(new ArrayList<>());
        overallPrice = 0.0;
    }

    public OrderItem getCurrentOrderItem() {
        return currentOrderItem;
    }

    public void setCurrentOrderItem(OrderItem currentOrderItem) {
        this.currentOrderItem = currentOrderItem;
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

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public OrderItem getOrderToBeManaged() {
        return orderToBeManaged;
    }

    public void setOrderToBeManaged(OrderItem orderToBeManaged) {
        this.orderToBeManaged = orderToBeManaged;
    }

    public List<String> getOrderToBeManagedAddress() {
        return orderToBeManagedAddress;
    }

    public void setOrderToBeManagedAddress(List<String> orderToBeManagedAddress) {
        this.orderToBeManagedAddress = orderToBeManagedAddress;
    }

    public Map<Long, String> getStatuses() {
        return statuses;
    }

    public void setStatuses(Map<Long, String> statuses) {
        this.statuses = statuses;
    }

    public Long getChosenStatus() {
        return chosenStatus;
    }

    public void setChosenStatus(Long chosenStatus) {
        this.chosenStatus = chosenStatus;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<String> getStatusNames() {
        return statusNames;
    }

    public void setStatusNames(List<String> statusNames) {
        this.statusNames = statusNames;
    }
}
