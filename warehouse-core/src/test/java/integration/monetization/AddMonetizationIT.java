package integration.monetization;

import hu.neuron.mentoring.clientapi.entity.Item;
import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.MonetizationService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class AddMonetizationIT {

    @Autowired
    ProductService productService;

    @Autowired
    MonetizationService monetizationService;

    @Test
    public void testAddMonetization(){
        Product product = new Product();
        product.setName("test");
        product.setAmount(10);
        productService.addProduct(product);

        Monetization testMonetization = new Monetization();
        Item testItem = new Item();
        testItem.setProduct(product);
        testItem.setQuantity(5);
        testMonetization.setItems(List.of(testItem));
        monetizationService.save(testMonetization);

        assertEquals(1,monetizationService.findAll().size());
    }
}
