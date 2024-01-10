package integration.user;

import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UserService;
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
public class AddUserIT {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Test
    public void testAddUser(){
        Role testRole = new Role();
        testRole.setName("test");
        roleService.save(testRole);

        User testUser = new User();
        testUser.setName("testUser");
        testUser.setRoles(new ArrayList<>(List.of(roleService.findByName("test"))));
        userService.save(testUser);

        assertEquals(3,userService.findAll().size());

    }

}
