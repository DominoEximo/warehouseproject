package integration.offer;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.CategoryService;
import hu.neuron.mentoring.clientapi.service.OfferService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.UnitService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class FindOfferByCategoryIT {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UnitService unitService;

    @Autowired
    ProductService productService;

    @Autowired
    OfferService offerService;

    @Test
    public void testFindOfferByCategory(){

        categoryService.setUpMockedData();
        unitService.setUpMockedData();
        productService.setUpMockedData();

        Offer offer1 = new Offer();
        Product product1 = new Product("test1",categoryService.findByName("Hus"),10,unitService.findByName("kg"), BigDecimal.valueOf(10),BigDecimal.valueOf(20),"test1");
        productService.addProduct(product1);
        offer1.setProduct(product1);
        offerService.save(offer1);

        Offer offer2 = new Offer();
        Product product2 = new Product("test2",categoryService.findByName("Hus"),10,unitService.findByName("kg"), BigDecimal.valueOf(10),BigDecimal.valueOf(20),"test2");
        productService.addProduct(product2);
        offer2.setProduct(product2);
        offerService.save(offer2);

        Offer offer3 = new Offer();
        Product product3 = new Product("test3",categoryService.findByName("Gyumolcs"),10,unitService.findByName("kg"), BigDecimal.valueOf(10),BigDecimal.valueOf(20),"test3");
        productService.addProduct(product3);
        offer3.setProduct(product3);
        offerService.save(offer3);

        assertEquals(2,offerService.findAllByProductCategory("Hus").size());
        assertEquals(1,offerService.findAllByProductCategory("Gyumolcs").size());

    }

}
