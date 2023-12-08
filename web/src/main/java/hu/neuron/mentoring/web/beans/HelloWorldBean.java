package hu.neuron.mentoring.web.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class HelloWorldBean {

    private static final Logger logger = LogManager.getLogger(HelloWorldBean.class);

    private String msg;

    private Integer number1;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    @PostConstruct
    private void init() {
        try {
            msg = "Hello World!! JFS example.. ";
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }
}