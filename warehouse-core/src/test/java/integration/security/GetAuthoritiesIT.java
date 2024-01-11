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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
@Transactional
@Rollback
public class GetAuthoritiesIT {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    public void testGetAuthorities(){
        roleService.setUpMockedData();
        userService.setUpMockedData();

        assertEquals(2,userDetailsService.loadUserByUsername("admin").getAuthorities().size());
        assertEquals(1,userDetailsService.loadUserByUsername("user").getAuthorities().size());
    }

}
