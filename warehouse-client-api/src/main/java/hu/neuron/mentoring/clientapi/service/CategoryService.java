package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findById(long id);

    Category findByName(String name);

    List<Category> findAll();

    void save(Category category);

    void delete(Category category);


}
