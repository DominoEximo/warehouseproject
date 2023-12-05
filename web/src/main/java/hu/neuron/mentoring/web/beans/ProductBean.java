package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.client_api.Category;
import hu.neuron.mentoring.client_api.Product;
import hu.neuron.mentoring.client_api.Unit;
import hu.neuron.mentoring.client_api.dao.CategoryDAO;
import hu.neuron.mentoring.client_api.dao.ProductDAO;
import hu.neuron.mentoring.client_api.dao.UnitDAO;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class ProductBean implements Serializable {

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
        categories = categoryDAO.getAll().stream().map(Category::getCategoryName).collect(Collectors.toList());
        category = "Hus";
        units = unitDAO.getAll().stream().map(Unit::getUnitName).collect(Collectors.toList());
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
