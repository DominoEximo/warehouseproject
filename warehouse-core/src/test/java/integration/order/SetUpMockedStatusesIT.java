package integration.order;

import hu.neuron.mentoring.clientapi.entity.*;
import hu.neuron.mentoring.clientapi.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class SetUpMockedStatusesIT {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OfferService offerService;

    @Autowired
    OrderService orderService;

    @Autowired
    StatusService statusService;

    @Test
    public void testSetUpMockedStatuses(){
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

        statusService.setUpMockedData();

        OrderItem order = new OrderItem();
        Shipment testShipment = new Shipment();
        testShipment.setOffer(offer);
        testShipment.setQuantity(1);
        order.setOrderedItems(new ArrayList<>(List.of(testShipment)));
        order.setUser(test);
        order.setStatus(statusService.findByName("Pending"));
        orderService.save(order);

        assertEquals("Pending",orderService.findById(order.getId()).getStatus().getStatusName());
    }
}
