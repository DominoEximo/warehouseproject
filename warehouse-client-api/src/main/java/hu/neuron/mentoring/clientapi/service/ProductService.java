package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;

import java.io.Serializable;
import java.util.List;


public interface ProductService extends Serializable {

    List<Product> getProducts(int page, int length);

    void addProduct(Product product);

    void deleteProduct(long id);

    List<Category> getCategories();
    List<Unit> getUnits();

    List<Product> getByCategoryPaginated(int page,int length,Category category);
}
