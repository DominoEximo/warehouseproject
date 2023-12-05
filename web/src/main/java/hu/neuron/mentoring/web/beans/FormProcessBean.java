package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.dao.CategoryDAO;
import hu.neuron.mentoring.clientapi.dao.ProductDAO;
import hu.neuron.mentoring.clientapi.dao.UnitDAO;
import hu.neuron.mentoring.clientapi.entity.Product;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@ManagedBean
@SessionScoped
public class FormProcessBean implements Serializable {

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
            ProductDAO.getInstance().save(product);
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
