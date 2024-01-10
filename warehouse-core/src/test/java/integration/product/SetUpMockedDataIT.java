package integration.product;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class SetUpMockedDataIT {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UnitService unitService;

    @Test
    public void testSetUpMockedData(){
        categoryService.setUpMockedData();
        unitService.setUpMockedData();
        productService.setUpMockedData();

        assertEquals(4,categoryService.findAll().size());
        assertEquals(2,unitService.findAll().size());
        assertNotEquals(0,productService.getAll().size());
    }

}
