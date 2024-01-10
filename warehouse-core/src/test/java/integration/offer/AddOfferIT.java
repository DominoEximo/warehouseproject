package integration.offer;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Product;
import hu.neuron.mentoring.clientapi.service.OfferService;
import hu.neuron.mentoring.clientapi.service.ProductService;
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
public class AddOfferIT {

    @Autowired
    ProductService productService;

    @Autowired
    OfferService offerService;


    @Test
    public void testAddOffer(){
        Product product = new Product();
        product.setName("test");
        productService.addProduct(product);

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setPrice(10.0);
        offerService.save(offer);

        assertEquals(1,offerService.findAll().size());
    }

}
