package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.OfferService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import hu.neuron.mentoring.core.service.OfferServiceImpl;
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
public class OfferBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(OfferBean.class);

    @Autowired
    private OfferService offerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;

    private List<Offer> offerList;


    private Map<Long,String> products;

    private int page = 1;

    private int length = 5;

    @PostConstruct
    public void init(){
        try {
            offerList = offerService.findAll();
            products = new LinkedHashMap<Long,String>();
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

    public void getProductList(){
        products.clear();
        for (Product product : productService.getAll()){
            products.put(product.getId(),product.getName());
        }
    }

    public void nextPage(){
        if(offerService.findAllPaginated(page,length).size() / length >= page){
            page += 1;
            loadOffers();
        }
    }
    public void prevPage(){
        if(page > 1){
            page -= 1;
            loadOffers();
        }

    }

    public void loadOffers(){
        String transactionName = "loadOffers";
        try {
            logger.info("Transaction '{}' started in OfferBean", transactionName);
            offerList = null;
            offerList = offerService.findAllPaginated(page,length);
            logger.info("Transaction '{}' completed successfully in OfferBean", transactionName);
        }catch (Exception e){
            logger.error("Transaction '{}' failed in OfferBean: {}",transactionName,e.getMessage());
        }

    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public Map<Long, String> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, String> products) {
        this.products = products;
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
}
