package hu.neuron.mentoring.clientapi.dao;



import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CategoryDAO implements DAO<Category>{

    static EntityManagerFactory emf;
    static EntityManager em;

    private static CategoryDAO instance = null;

    public static synchronized CategoryDAO getInstance() {
        if (instance == null){
            emf = Persistence
                    .createEntityManagerFactory("ProductPU");;
            em = emf.createEntityManager();
            instance = new CategoryDAO();
            DatasourceConfig.getInstance();
        }

        return  instance;
    }

    @Override
    public Category findById(int id) {
        return em.find(Category.class,id);
    }

    public Category findByName(String name){
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.categoryName = :name" , Category.class);

        Category category = query.setParameter("name", name).getSingleResult();
        return category;
    }

    @Override
    public List<Category> getAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public void save(Category category) {
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    @Override
    public Category update(Category category) {
        return em.merge(category);

    }

    @Override
    public void delete(Category category) {
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }
}
