package hu.neuron.mentoring.web.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;
@Component
@SessionScoped
public class LocaleBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(LocaleBean.class);

    private String locale = "en";

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String changeLanguage(String locale) {
        this.locale = locale;
        FacesContext.getCurrentInstance().getViewRoot()
                .setLocale(new Locale(this.locale));
        return locale;
    }


}
