package hu.neuron.mentoring.web.beans;


import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.core.dao.CategoryDAO;
import hu.neuron.mentoring.core.dao.ProductDAO;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.core.service.ProductServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class ProductBean  implements Serializable {

    @Autowired
    ProductService productService;


    @Autowired
    CategoryService categoryService;

    private List<Product> products;

    private int length = 5;

    private String category;

    private String unit;

    private List<String> categories;

    private List<String> units;

    private int page = 1;

    @PostConstruct
    public void init(){
        productService.setUpMockedData();
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
        products = productService.getByCategoryPaginated(page,length,categoryService.findByName(category));
    }

    public void nextPage(){
            if(productService.getAllByCategory(categoryService.findByName(category)).size() / length >= page){
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
