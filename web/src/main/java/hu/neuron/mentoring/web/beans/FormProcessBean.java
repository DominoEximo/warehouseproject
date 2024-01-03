package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.entity.*;
import hu.neuron.mentoring.clientapi.service.*;
import hu.neuron.mentoring.core.dao.ProductDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScoped
public class FormProcessBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(FormProcessBean.class);

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UnitService unitService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MonetizationService monetizationService;

    @Autowired
    OfferService offerService;

    private Product product;

    private User user;

    private Monetization monetizationToBeManaged;

    private Offer offerToBeManaged;

    private String chosenCategory;

    private String chosenUnit;

    private String chosenRole;

    private Long chosenProduct;

    private Long chosenOfferProduct;

    private int productQuantity = 0;

    private Boolean valid = true;

    private Boolean enabled = true;

    @PostConstruct
    public void init(){
        try {
            user = new User();
            product = new Product();
            monetizationToBeManaged = new Monetization();
            offerToBeManaged = new Offer();
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getChosenCategory() {
        return chosenCategory;
    }

    public void setChosenCategory(String chosenCategory) {
        this.chosenCategory = chosenCategory;
    }

    public String getChosenUnit() {
        return chosenUnit;
    }

    public void setChosenUnit(String chosenUnit) {
        this.chosenUnit = chosenUnit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChosenRole() {
        return chosenRole;
    }

    public void setChosenRole(String chosenRole) {
        this.chosenRole = chosenRole;
    }


    public Monetization getMonetizationToBeManaged() {
        return monetizationToBeManaged;
    }

    public void setMonetizationToBeManaged(Monetization monetization) {
        this.monetizationToBeManaged = monetization;
    }

    public Long getChosenProduct() {
        return chosenProduct;
    }

    public void setChosenProduct(Long chosenProduct) {
        this.chosenProduct = chosenProduct;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Offer getOfferToBeManaged() {
        return offerToBeManaged;
    }

    public void setOfferToBeManaged(Offer offerToBeManaged) {
        this.offerToBeManaged = offerToBeManaged;
    }

    public Long getChosenOfferProduct() {
        return chosenOfferProduct;
    }

    public void setChosenOfferProduct(Long chosenOfferProduct) {
        this.chosenOfferProduct = chosenOfferProduct;
    }

    public void processForm(){
        String transactionName = "addProduct";
        try {
            logger.info("Transaction '{}' started in FormProcessBean", transactionName);

            if(product.getName() != null) {
                product.setCategory(categoryService.findByName(chosenCategory));
                product.setUnit(unitService.findByName(chosenUnit));
                productService.addProduct(product);
            }
            logger.info("Transaction '{}' completed successfully in FormProcessBean", transactionName);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Product added successfully");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }catch (Exception e){
            logger.error("Transaction '{}' failed in FormProcessBean: {}",transactionName,e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error during adding product");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
        setProduct(new Product());

    }

    public void processUserForm(){
        String transactionName = "addUser";
        validateInput("Username",user.getName());
        validateInput("Password",user.getPassword());
        validateInput("Email",user.getEmail());
        validateInput("Phone Number",user.getPhoneNumber());
        validateInput("Birth Date",user.getBirthDate().toString());
        validateInput("Gender",user.getGender().toString());
        if(valid){
            try {
                logger.info("Transaction '{}' started in FormProcessBean", transactionName);

                List<Role> roles;
                if(user.getRoles() == null){
                    roles = new ArrayList<>();
                }else {
                    roles = user.getRoles();
                }
                roles.add(roleService.findByName(chosenRole));
                user.setRoles(roles);
                userService.save(user);

                logger.info("Transaction '{}' completed successfully in FormProcessBean", transactionName);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User added successfully");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }catch (Exception e){
                logger.error("Transaction '{}' failed in FormProcessBean: {}",transactionName,e.getMessage());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error during adding user");
                PrimeFaces.current().dialog().showMessageDynamic(message);
            }

        }else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Invalid credentials");
            PrimeFaces.current().dialog().showMessageDynamic(message);
        }
        valid = true;
    }

    public void processMonetizationForm(){
        String transactionName = "addMonetization";
        validateInput("Date",monetizationToBeManaged.getDate().toString());
        validateInput("Product",chosenProduct.toString());
        validateInput("Quantity", String.valueOf(productQuantity));
        if (valid){
            try {
                logger.info("Transaction '{}' started in FormProcessBean", transactionName);

                Item item = new Item();
                item.setProduct(productService.getproductById(chosenProduct));
                item.setQuantity(productQuantity);
                List<Item> items = new ArrayList<>();
                items.add(item);
                monetizationToBeManaged.setItems(items);
                productService.updateProductQuantity(chosenProduct,productQuantity);
                monetizationService.save(monetizationToBeManaged);

                logger.info("Transaction '{}' completed successfully in FormProcessBean", transactionName);
                if(enabled){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Monetization added successfully");
                    PrimeFaces.current().dialog().showMessageDynamic(message);
                }else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Monetization modified successfully");
                    PrimeFaces.current().dialog().showMessageDynamic(message);
                }

            }catch (Exception e){
                logger.error("Transaction '{}' failed in FormProcessBean: {}",transactionName,e.getMessage());
                if(enabled){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error during adding monetization");
                    PrimeFaces.current().dialog().showMessageDynamic(message);
                }else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error during modification of monetization");
                    PrimeFaces.current().dialog().showMessageDynamic(message);
                }
            }
        }

        clearMonetization();
        valid = true;
    }

    public void processOfferForm(){
        String transactionName = "addOffer";
        validateInput("Product",chosenOfferProduct.toString());
        validateInput("Start Date",offerToBeManaged.getStartDate().toString());
        validateInput("End Date",offerToBeManaged.getEndDate().toString());
        validateInput("Price",offerToBeManaged.getPrice().toString());
        if(offerToBeManaged.getEndDate().getTime() < offerToBeManaged.getStartDate().getTime()){
            valid = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Invalid Date");
            PrimeFaces.current().dialog().showMessageDynamic(message);

        }


        if (valid){
            try {
                logger.info("Transaction '{}' started in FormProcessBean", transactionName);
                offerToBeManaged.setProduct(productService.getproductById(chosenOfferProduct));
                offerService.save(offerToBeManaged);

                logger.info("Transaction '{}' completed successfully in FormProcessBean", transactionName);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Offer added successfully");
                PrimeFaces.current().dialog().showMessageDynamic(message);


            }catch (Exception e){
                logger.error("Transaction '{}' failed in FormProcessBean: {}",transactionName,e.getMessage());

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Error during adding offer");
                PrimeFaces.current().dialog().showMessageDynamic(message);


            }
        }

        clearOffer();
        valid = true;
    }

    private void validateInput(String subject, String value) {
        try {
            // Perform your validation logic here
            // For illustration purposes, let's assume a simple validation (e.g., not null)
            if (value != null) {
                if (subject.equals("Email")){
                    if (!value.contains("@")){
                        logger.error("Validation failed for {}: Value is in wrong format", subject);
                        valid = false;
                    }else {
                        // Log successful validation
                        logger.info("Validation successful for {}: {}", subject, maskSensitiveInfo(subject,value));
                    }
                }
            } else {
                // Log validation failure
                logger.warn("Validation failed for {}: Value is null", subject);
                valid = false;
            }
        } catch (Exception e) {
            // Log validation error
            logger.error("Error during validation for {}: {}", subject, e.getMessage());
        }
    }

    private String maskSensitiveInfo(String subject,String value) {
        // Mask emails and passwords
        if ("Password".equalsIgnoreCase(subject)) {
            // Mask the entire password
            return "*****";
        } else if (value.contains("@")) {
            String[] parts = value.split("@");
            if (parts.length == 2) {
                String maskedUsername = parts[0].replaceAll(".", "*");
                return maskedUsername + "@" + parts[1];
            }
        }
        return value; // No masking for other values
    }

    public void setUpMonetizationToBeManaged(Monetization monetization){
        monetizationToBeManaged.setId(monetization.getId());
        monetizationToBeManaged.setDate(monetization.getDate());
        chosenProduct = monetization.getItems().get(0).getProduct().getId();
        productQuantity = monetization.getItems().get(0).getQuantity();
        enabled = false;
    }

    private void clearMonetization(){
        monetizationToBeManaged = new Monetization();
        chosenProduct = null;
        productQuantity = 0;
        enabled = true;
    }

    private void clearOffer(){
        offerToBeManaged = new Offer();
        chosenOfferProduct = null;
    }


}
