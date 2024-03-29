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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class OfferBean implements Serializable {

    private static final String TRANSACTION_STARTED_MESSAGE = "Transaction '{}' started in OfferBean";

    private static final String TRANSACTION_SUCCESS_MESSAGE = "Transaction '{}' completed successfully in OfferBean";

    private static final String TRANSACTION_FAILED_MESSAGE = "Transaction '{}' failed in OfferBean";


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

    private Date searchDate = new Date();

    private String selectedCategory;

    @PostConstruct
    public void init(){
        try {
            offerList = offerService.findAll();
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
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
            offerList = null;
            offerList = offerService.findAllPaginated(page,length);
            offerList = offerList.stream().filter(x -> (x.getStartDate().getTime() <= searchDate.getTime() && x.getEndDate().getTime() >= searchDate.getTime())).collect(Collectors.toList());
            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
        }

    }

    public void loadAvailableOffers(){
        String transactionName = "loadAvailableOffers";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
            offerList = null;
            offerList = offerService.findAll();
            offerList = offerList.stream().filter(x -> (x.getStartDate().getTime() <= new Date().getTime() && x.getEndDate().getTime() >= new Date().getTime())).collect(Collectors.toList());
            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
        }
    }

    public void refreshOffers(){
        String transactionName = "refreshOffers";
        try {
            logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
            offerList = null;
            offerList = offerService.findAll();
            logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
        }
    }

    public void loadFilteredOffers(){
        String transactionName = "filterOffersByCategory";
        try {
            if(selectedCategory == null || selectedCategory.isEmpty()){
                loadAvailableOffers();
            }
            else {
                logger.info(TRANSACTION_STARTED_MESSAGE.replace("{}",transactionName));
                offerList = null;
                offerList = offerService.findAllByProductCategory(selectedCategory);
                offerList = offerList.stream().filter(x -> (x.getStartDate().getTime() <= new Date().getTime() && x.getEndDate().getTime() >= new Date().getTime())).collect(Collectors.toList());
                logger.info(TRANSACTION_SUCCESS_MESSAGE.replace("{}",transactionName));
            }

        }catch (Exception e){
            logger.error(TRANSACTION_FAILED_MESSAGE.replace("{}",transactionName),e.getMessage());
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

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
