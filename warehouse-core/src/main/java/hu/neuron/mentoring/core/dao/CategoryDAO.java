package hu.neuron.mentoring.core.dao;



import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.core.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CategoryDAO implements DAO<Category>{

    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDAO() {
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).get();
    }

    public Category findByName(String name){
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public Category findById(int id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return em.merge(category);

    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
