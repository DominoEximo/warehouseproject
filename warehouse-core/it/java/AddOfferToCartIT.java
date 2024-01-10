import hu.neuron.mentoring.clientapi.entity.*;
import hu.neuron.mentoring.clientapi.service.OfferService;
import hu.neuron.mentoring.clientapi.service.OrderService;
import hu.neuron.mentoring.clientapi.service.ProductService;
import hu.neuron.mentoring.clientapi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
public class AddOfferToCartIT {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OfferService offerService;

    @Autowired
    OrderService orderService;

    @Test
    public void testAddingToCartProcess(){
        User test = new User();
        test.setName("test");
        userService.save(test);

        Product product = new Product();
        product.setName("test");
        productService.addProduct(product);

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setPrice(10.0);
        offerService.save(offer);

        OrderItem order = new OrderItem();
        Shipment testShipment = new Shipment();
        testShipment.setOffer(offer);
        testShipment.setQuantity(1);
        order.setOrderedItems(new ArrayList<>(List.of(testShipment)));
        order.setUser(test);
        orderService.save(order);

        assertEquals(1,orderService.findAll().size());

    }
}
