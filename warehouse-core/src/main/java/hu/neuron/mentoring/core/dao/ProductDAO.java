package hu.neuron.mentoring.core.dao;



import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.core.service.ProductServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Component
@SessionScope
public class ProductDAO implements Serializable, DAO<Product>{

    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @Autowired
    ProductService productService;


    public ProductDAO() {

    }

    @Override
    public void save(Product product){
        productService.addProduct(product);
    }


    @Override
    public Product findById(int id) {
        return em.find(Product.class,id);
    }

    @Override
    public List<Product> getAll() {
        Query query = em.createQuery("From Product ");
        int pageNumber = 1;
        int pageSize = 10;
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List <Product> productList = query.getResultList();
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
    @Override
    public void delete(Product product) {
       productService.deleteProduct(product.getId());
    }
    @Override
    public Product update(Product product) {
        return em.merge(product);

    }

    public List<Product> getAllByCategory(int category){
        Query query = em.createQuery("select p From Product p where p.category.id ="+ category);
        List <Product> productList = query.getResultList();
        return productList;
    }

    public void setUpMockedData(){
        DatasourceConfig.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        UnitDAO unitDAO = UnitDAO.getInstance();
        Product test = new Product("Csirke",categoryDAO.findByName("Hus"),10, unitDAO.findByName("kg"),BigDecimal.valueOf(40),BigDecimal.valueOf(300),"Hús");
        Product test2 = new Product("Körte",categoryDAO.findByName("Gyumolcs"),10, unitDAO.findByName("kg"),BigDecimal.valueOf(2),BigDecimal.valueOf(2000),"körtee");
        Product test3 = new Product("Alma",categoryDAO.findByName("Gyumolcs"),10, unitDAO.findByName("lbs"),BigDecimal.valueOf(10),BigDecimal.valueOf(10),"Almaa");
        Product test4 = new Product("Csirke3",categoryDAO.findByName("Hus"),10, unitDAO.findByName("kg"),BigDecimal.valueOf(4),BigDecimal.valueOf(300),"Hús");

        for (int i = 0; i< 10; i++){
            save(new Product("Csirke3",categoryDAO.findByName("Hus"),10, unitDAO.findByName("kg"),BigDecimal.valueOf(4),BigDecimal.valueOf(300),"Hús"));

        }
        save(test);
        save(test2);
        save(test3);
        save(test4);
    }
    public List<Product> getAllPageinated(int pageNumber, int pageSize) {
        Query query = em.createQuery("From Product ");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List <Product> productList = query.getResultList();
        return productService.getProducts(pageNumber,pageSize);
    }
    public List<Product> getByCategoryPageinated(int pageNumber, int pageSize, Category category) {

        return productService.getByCategoryPaginated(pageNumber,pageSize,category);
    }
}
