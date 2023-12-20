package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Stock;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.core.dao.CategoryDAO;
import hu.neuron.mentoring.core.dao.ProductDAO;
import hu.neuron.mentoring.core.dao.UnitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service("productService")
public class ProductServiceImpl  implements ProductService, Serializable {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    UnitDAO unitDao;
    @Override
    public List<Product> getProducts(int page, int length) {
        List<Product> products = productDAO.getAllPaginated(page,length);
        return products;
    }

    @Override
    public void addProduct(Product product) {
        productDAO.save(product);

    }

    @Override
    public void deleteProduct(long id) {
        productDAO.delete(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryDAO.getAll();
    }

    @Override
    public List<Unit> getUnits() {
        return unitDao.getAll();
    }

    @Override
    public List<Product> getByCategoryPaginated(int page, int length, Category category) {
        List<Product> products = (List<Product>) productDAO.getByCategoryPaginated(page,length,category);
        return products;
    }

    @Override
    public List<Product> getAllByCategory(Category category) {
        List<Product> products = productDAO.getAllByCategory(category);
        return products;
    }

    @Override
    public void setUpMockedData() {
        productDAO.setUpMockedData();
    }

    @Override
    public void updateProductQuantity(Long productId, int newQuantity) {
        Product product = productDAO.findById(productId);

        if (product != null) {
            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setQuantity(newQuantity);
            stock.setDate(new Date());

            product.getPastData().add(stock);
            product.setAmount(newQuantity);

            productDAO.save(product);
        }
    }


}
