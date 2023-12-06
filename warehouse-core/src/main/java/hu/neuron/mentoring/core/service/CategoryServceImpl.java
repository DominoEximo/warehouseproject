package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.core.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public Category findById(long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.getAll();
    }

    @Override
    public void save(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryDAO.delete(category.getId());
    }
}
