package hu.neuron.mentoring.web.beans;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.ws.rs.ApplicationPath;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;

@Lazy
@Component
@ApplicationScoped
public class LocaleBean implements Serializable {

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

    @PostConstruct
    public void init(){
        FacesContext.getCurrentInstance().getViewRoot()
                .setLocale(new Locale(this.locale));
    }
}
