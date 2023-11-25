package hu.neuron.mentoring.web.service;

import com.google.gson.JsonObject;
import hu.neuron.mentoring.client_api.Category;
import hu.neuron.mentoring.client_api.Product;
import hu.neuron.mentoring.client_api.Unit;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(int page, int length);

    void addProduct(Product product);

    List<Category> getCategories();
    List<Unit> getUnits();

}
