package hu.neuron.mentoring.web.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
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
