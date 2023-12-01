package hu.neuron.mentoring.web.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

@Named
@RequestScoped
public class FormView {

    public void viewResponsive() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .modal(true)
                .fitViewport(true)
                .responsive(true)
                .width("900px")
                .contentWidth("100%")
                .resizeObserver(true)
                .resizeObserverCenter(true)
                .resizable(false)
                .styleClass("max-w-screen")
                .iframeStyleClass("max-w-screen")
                .build();

        PrimeFaces.current().dialog().openDynamic("ProductForm", options, null);
    }
}
