package hu.neuron.mentoring.core.dao;




import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.core.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CategoryDAO implements DAO<Category>{

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDAO() {
    }
    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).get();
    }

    public Category findByName(String name){
        return categoryRepository.findByCategoryName(name);
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
        return categoryRepository.save(category);

    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    public void setUpMockedData(){
        save(new Category("Hus"));
        save(new Category("Gyumolcs"));
        save(new Category("Valami"));
        save(new Category("Ruha"));

    }
}
