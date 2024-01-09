package hu.neuron.mentoring.core.dao;




import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.core.repositories.CategoryRepository;
import hu.neuron.mentoring.core.repositories.ProductRepository;
import hu.neuron.mentoring.core.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO implements Serializable, DAO<Product>{

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

        Optional<Product> found = productRepository.findById(id);
        Product product = new Product();
        if (found.isPresent()){
            product.setId(found.get().getId());
            product.setAmount(found.get().getAmount());
            product.setCategory(found.get().getCategory());
            product.setUnit(found.get().getUnit());
            product.setName(found.get().getName());
            product.setDescription(found.get().getDescription());
            product.setPastData(found.get().getPastData());
            product.setPurchasePrice(found.get().getPurchasePrice());
            product.setSellPrice(found.get().getSellPrice());
        }

        return product;
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
        return productRepository.save(product);

    }

    public List<Product> getAllByCategory(Category category){
        return productRepository.getAllByCategory(category);
    }

    public void setUpMockedData(){

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
