package hu.neuron.mentoring.web.service;



import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(int page, int length);

    void addProduct(Product product);

    List<Category> getCategories();
    List<Unit> getUnits();

}
