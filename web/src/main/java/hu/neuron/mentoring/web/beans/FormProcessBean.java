package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import hu.neuron.mentoring.clientapi.service.UserService;
import hu.neuron.mentoring.core.dao.ProductDAO;
import hu.neuron.mentoring.clientapi.entity.Product;
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
    ProductDAO productDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UnitService unitService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private Product product;

    private User user;

    private String chosenCategory;

    private String chosenUnit;

    private String chosenRole;

    private Boolean valid = true;

    @PostConstruct
    public void init(){
        try {
            user = new User();
            product = new Product();
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

    public void processForm(){
        String transactionName = "addProduct";
        try {
            logger.info("Transaction '{}' started in FormProcessBean", transactionName);

            if(product.getName() != null) {
                product.setCategory(categoryService.findByName(chosenCategory));
                product.setUnit(unitService.findByName(chosenUnit));
                productDAO.save(product);
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


}
