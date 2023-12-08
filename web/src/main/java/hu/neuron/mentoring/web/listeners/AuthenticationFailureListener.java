package hu.neuron.mentoring.web.listeners;

import jakarta.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        // Log authentication failure
        System.out.println("Authentication failure for user '" + username + "'.");
    }
}
