package integration.product;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class UpdateProductQuantityIT {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UnitService unitService;

    @Test
    public void testUpdateProductQuantity(){
        Unit testUnit = new Unit("testUnit");
        unitService.save(testUnit);

        Category testCategory = new Category("testCategory");
        categoryService.save(testCategory);

        Product testProduct = new Product();
        testProduct.setName("test");
        testProduct.setUnit(unitService.findByName("testUnit"));
        testProduct.setCategory(categoryService.findByName("testCategory"));
        testProduct.setAmount(10);
        productService.addProduct(testProduct);

        productService.updateProductQuantity(testProduct.getId(),22);

        assertEquals(22, productService.getproductById(testProduct.getId()).getAmount());
    }

}
