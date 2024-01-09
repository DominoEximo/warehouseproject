package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.MonetizationService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@SessionScoped
public class MonetizationBean implements Serializable{

    private static final Logger logger = LogManager.getLogger(MonetizationBean.class);

    @Autowired
    private MonetizationService monetizationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;

    private List<Monetization> monetizationList;


    private Map<Long,String> products;

    private int page = 1;

    private int length = 5;

    @PostConstruct
    public void init(){
        try {
            monetizationList = monetizationService.findAll();
            products = new LinkedHashMap<>();
            if (productService.getAll().isEmpty()){
                categoryService.setUpMockedData();
                unitService.setUpMockedData();
                productService.setUpMockedData();
            }
            getProductList();
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }

    }

    public void loadMonetizations(){
        String transactionName = "loadMonetizations";
        try {
            logger.info("Transaction '{}' started in MonetizationBean", transactionName);
            monetizationList = null;
            monetizationList = monetizationService.findAllPaginated(page,length);
            logger.info("Transaction '{}' completed successfully in MonetizationBean", transactionName);
        }catch (Exception e){
            logger.error("Transaction '{}' failed in MonetizationBean: {}",transactionName,e.getMessage());
        }

    }

    public void nextPage(){
        if(monetizationService.findAllPaginated(page,length).size() / length >= page){
            page += 1;
            loadMonetizations();
        }
    }
    public void prevPage(){
        if(page > 1){
            page -= 1;
            loadMonetizations();
        }

    }

    public void getProductList(){
        products.clear();
        for (Product product : productService.getAll()){
            products.put(product.getId(),product.getName());
        }
    }

    public List<Monetization> getMonetizationList() {
        return monetizationList;
    }


    public int getPage() {
        return page;
    }

    public int getLength() {
        return length;
    }

    public void setMonetizationList(List<Monetization> monetizationList) {
        this.monetizationList = monetizationList;
    }


    public Map<Long,String> getProducts() {
        return products;
    }

    public void setProducts(Map<Long,String> products) {
        this.products = products;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
