package hu.neuron.mentoring.web.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LogManager.getLogger(AuthenticationEventListener.class);
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent successEvent) {
        String username = successEvent.getAuthentication().getName();
        ThreadContext.put("username", username);
        logger.info("User  {}  successfully authenticated.",username);
    }

}
