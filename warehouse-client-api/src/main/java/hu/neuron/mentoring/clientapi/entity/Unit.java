package hu.neuron.mentoring.clientapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitName;


    public Unit(String unitName) {
        this.unitName = unitName;
    }

    public Unit() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


}
