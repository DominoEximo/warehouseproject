package integration.security;

import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class LoadUserByUsernameIT {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername(){
        roleService.setUpMockedData();
        userService.setUpMockedData();

        assertNotNull(userDetailsService.loadUserByUsername("user"));
        assertNotNull(userDetailsService.loadUserByUsername("admin"));
    }

}
