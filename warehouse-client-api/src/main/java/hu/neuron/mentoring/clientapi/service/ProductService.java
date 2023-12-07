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

    /**
     * Return all the present categories from the database
     * @return list of categories
     */
    List<Category> getCategories();

    /**
     * Return all the present units from the database
     * @return list of units
     */
    List<Unit> getUnits();

    /**
     * returns a list of filtered and paginated products
     * @param page page number
     * @param length size of the page
     * @param category category to filter by
     * @return a list of products
     */
    List<Product> getByCategoryPaginated(int page,int length,Category category);

    /**
     * returns a list of products by a given category
     * @param category the filter
     * @return a filterey list of products
     */
    List<Product> getAllByCategory(Category category);

    /**
     * fills the database with predefined data
     */
    void setUpMockedData();
}
