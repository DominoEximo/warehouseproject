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
public class FindOrderByStatusIT {

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
    public void testFindOrdersByStatus(){
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

        OrderItem order2 = new OrderItem();
        Shipment testShipment2 = new Shipment();
        testShipment2.setOffer(offer);
        testShipment2.setQuantity(1);
        order2.setOrderedItems(new ArrayList<>(List.of(testShipment2)));
        order2.setUser(test);
        order2.setStatus(statusService.findByName("Pending"));
        orderService.save(order2);

        OrderItem order3 = new OrderItem();
        Shipment testShipment3 = new Shipment();
        testShipment3.setOffer(offer);
        testShipment3.setQuantity(1);
        order3.setOrderedItems(new ArrayList<>(List.of(testShipment3)));
        order3.setUser(test);
        order3.setStatus(statusService.findByName("Shipped"));
        orderService.save(order3);

        assertEquals(2,orderService.findAllByStatus("Pending").size());
        assertEquals(1,orderService.findAllByStatus("Shipped").size());

    }
}
