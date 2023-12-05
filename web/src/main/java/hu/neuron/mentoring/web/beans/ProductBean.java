package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.dao.CategoryDAO;
import hu.neuron.mentoring.clientapi.dao.ProductDAO;
import hu.neuron.mentoring.clientapi.dao.UnitDAO;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.clientapi.service.ProductServiceImpl;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class ProductBean  implements Serializable {

    @Autowired
    ProductServiceImpl productService;

    private List<Product> products;

    private int length = 5;

    private String category;

    private String unit;

    private List<String> categories;

    private List<String> units;

    private int page = 1;

    @PostConstruct
    public void init(){
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        UnitDAO unitDAO = UnitDAO.getInstance();
        categories = productService.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toList());
        category = "Hus";
        units = productService.getUnits().stream().map(Unit::getUnitName).collect(Collectors.toList());
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCategory() {
        page = 1;
        return category;
    }

    public void setCategory(String category) {
        page = 1;
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public void loadProductsPaginatedFiltered(){
        products = null;
        products = ProductDAO.getInstance().getByCategoryPageinated(page,length,CategoryDAO.getInstance().findByName(category).getId().intValue());
    }

    public void nextPage(){
            if(ProductDAO.getInstance().getAllByCategory(CategoryDAO.getInstance().findByName(category).getId().intValue()).size() / length >= page){
                page += 1;
                loadProductsPaginatedFiltered();
            }



    }
    public void prevPage(){
        if(page > 1){
            page -= 1;
            loadProductsPaginatedFiltered();
        }

    }
}
