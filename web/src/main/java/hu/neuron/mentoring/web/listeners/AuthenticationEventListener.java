package hu.neuron.mentoring.web.listeners;

import jakarta.faces.application.FacesMessage;
import org.apache.logging.log4j.ThreadContext;
import org.primefaces.PrimeFaces;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent successEvent) {
        String username = successEvent.getAuthentication().getName();
        // Log successful authentication
        ThreadContext.put("username", username);
        System.out.println("User '" + username + "' successfully authenticated.");
    }

}
