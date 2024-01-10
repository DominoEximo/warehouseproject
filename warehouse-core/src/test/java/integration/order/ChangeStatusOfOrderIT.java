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
public class ChangeStatusOfOrderIT {

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
    public void testChangeStatusOfOrder(){
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

        Status testStatus = new Status("testStatus");
        Status newStatus = new Status("newStatus");

        OrderItem order = new OrderItem();
        Shipment testShipment = new Shipment();
        testShipment.setOffer(offer);
        testShipment.setQuantity(1);
        order.setOrderedItems(new ArrayList<>(List.of(testShipment)));
        order.setUser(test);
        order.setStatus(testStatus);
        orderService.save(order);

        order.setStatus(newStatus);

        orderService.update(order);

        assertEquals("newStatus",orderService.findById(order.getId()).getStatus().getStatusName());
    }

}

