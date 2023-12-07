package hu.neuron.mentoring.core.dao;



import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.core.repositories.CategoryRepository;
import hu.neuron.mentoring.core.repositories.ProductRepository;
import hu.neuron.mentoring.core.repositories.UnitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductDAO implements Serializable, DAO<Product>{

    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UnitRepository unitRepository;


    public ProductDAO() {

    }

    @Override
    public void save(Product product){
        productRepository.save(product);
    }


    @Override
    public Product findById(long id) {
        return em.find(Product.class,id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    @Override
    public void delete(long id) {
       productRepository.deleteById(id);
    }
    @Override
    public Product update(Product product) {
        return em.merge(product);

    }

    public List<Product> getAllByCategory(Category category){
        return productRepository.getAllByCategory(category);
    }

    public void setUpMockedData(){
        DatasourceConfig.getInstance();
        Product test = new Product("Csirke",categoryRepository.findByCategoryName("Hus"),10, unitRepository.findByUnitName("kg"),BigDecimal.valueOf(40),BigDecimal.valueOf(300),"Hús");
        Product test2 = new Product("Körte",categoryRepository.findByCategoryName("Gyumolcs"),10, unitRepository.findByUnitName("kg"),BigDecimal.valueOf(2),BigDecimal.valueOf(2000),"körtee");
        Product test3 = new Product("Alma",categoryRepository.findByCategoryName("Gyumolcs"),10, unitRepository.findByUnitName("lbs"),BigDecimal.valueOf(10),BigDecimal.valueOf(10),"Almaa");
        Product test4 = new Product("Csirke3",categoryRepository.findByCategoryName("Hus"),10, unitRepository.findByUnitName("kg"),BigDecimal.valueOf(4),BigDecimal.valueOf(300),"Hús");

        for (int i = 0; i< 10; i++){
            save(new Product("Csirke3",categoryRepository.findByCategoryName("Hus"),10, unitRepository.findByUnitName("kg"),BigDecimal.valueOf(4),BigDecimal.valueOf(300),"Hús"));

        }
        save(test);
        save(test2);
        save(test3);
        save(test4);
    }
    public List<Product> getAllPaginated(int pageNumber, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }
    public List<Product> getByCategoryPaginated(int pageNumber, int pageSize, Category category) {

        return productRepository.getAllByCategory(category, PageRequest.of(pageNumber-1,pageSize));
    }
}
