package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.core.dao.CategoryDAO;
import hu.neuron.mentoring.core.dao.ProductDAO;
import hu.neuron.mentoring.core.dao.UnitDAO;
import hu.neuron.mentoring.clientapi.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@SessionScoped
public class FormProcessBean implements Serializable {

    @Autowired
    ProductDAO productDAO;
    private Product product;

    private String chosenCategory;

    private String chosenUnit;

    @PostConstruct
    public void init(){
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
            product.setCategory(CategoryDAO.getInstance().findByName(chosenCategory));
            product.setUnit(UnitDAO.getInstance().findByName(chosenUnit));
            productDAO.save(product);
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


}
