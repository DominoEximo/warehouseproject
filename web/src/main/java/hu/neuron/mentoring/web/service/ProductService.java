package hu.neuron.mentoring.web.service;

import com.google.gson.JsonObject;
import hu.neuron.mentoring.client_api.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    void addProduct(Product product);
}
