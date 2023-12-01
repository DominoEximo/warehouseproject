package hu.neuron.mentoring.client_api.dao;

import hu.neuron.mentoring.client_api.Category;
import hu.neuron.mentoring.client_api.Product;
import hu.neuron.mentoring.client_api.Unit;
import hu.neuron.mentoring.client_api.datasource.DatasourceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.List;

public class ProductDAO implements DAO<Product>{

    static EntityManagerFactory emf;
    static EntityManager em;

    private static ProductDAO instance = null;

    public static synchronized ProductDAO getInstance() {
        if (instance == null){
            emf = Persistence
                    .createEntityManagerFactory("ProductPU");;
            em = emf.createEntityManager();
            instance = new ProductDAO();
            instance.setUpMockedData();
        }

        return  instance;
    }



    @Override
    public void save(Product product){
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
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
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
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

    private void setUpMockedData(){
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
        return productList;
    }
    public List<Product> getByCategoryPageinated(int pageNumber, int pageSize, int categoryId) {
        Query query = em.createQuery("select p From Product p where p.category.id ="+ categoryId);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List <Product> productList = query.getResultList();
        return productList;
    }
}
