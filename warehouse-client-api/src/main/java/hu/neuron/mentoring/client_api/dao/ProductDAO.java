package hu.neuron.mentoring.client_api.dao;

import hu.neuron.mentoring.client_api.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProductDAO {

    EntityManagerFactory emf;
    EntityManager em;


    public ProductDAO() {
        this.emf = Persistence
                .createEntityManagerFactory("ProductPU");;
        this.em = emf.createEntityManager();;
    }

    public void addProduct(Product product){
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }


    public List<Product> getAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void delete(Product product) {
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }

    public Product save(Product product) {
        return em.merge(product);

    }

}
