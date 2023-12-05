package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.clientapi.repositories.CategoryRepository;
import hu.neuron.mentoring.clientapi.repositories.ProductRepository;
import hu.neuron.mentoring.clientapi.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.Serializable;
import java.util.List;

@Service("productService")
public class ProductServiceImpl  implements ProductService, Serializable {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UnitRepository unitRepository;
    @Override
    public List<Product> getProducts(int page, int length) {
        List<Product> products = (List<Product>) productRepository.findAll(PageRequest.of(page-1,length));
        return products;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Unit> getUnits() {
        return unitRepository.findAll();
    }
}
