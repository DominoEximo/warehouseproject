package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import hu.neuron.mentoring.clientapi.service.UserService;
import hu.neuron.mentoring.core.dao.CategoryDAO;
import hu.neuron.mentoring.core.dao.ProductDAO;
import hu.neuron.mentoring.core.dao.UnitDAO;
import hu.neuron.mentoring.clientapi.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScoped
public class FormProcessBean implements Serializable {

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

    @PostConstruct
    public void init(){
        user = new User();
        product = new Product();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void processForm(){
        if(product.getName() != null){
            product.setCategory(categoryService.findByName(chosenCategory));
            product.setUnit(unitService.findByName(chosenUnit));
            productDAO.save(product);
        }
        setProduct(new Product());

    }

    public void processUserForm(){
        if(user.getName() != null){
            List<Role> roles;
            if(user.getRoles() == null){
                roles = new ArrayList<>();
            }else {
                roles = user.getRoles();
            }
            roles.add(roleService.findByName(chosenRole));
            user.setRoles(roles);
            userService.save(user);
        }
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
}
