package integration.user;

import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UserService;
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
public class SetUpMockedDataIT {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Test
    public void testSetUpMockedData(){

        roleService.setUpMockedData();
        userService.setUpMockedData();

        assertEquals(2, roleService.findAll().size());
        assertEquals(2,userService.findAll().size());

    }

}
