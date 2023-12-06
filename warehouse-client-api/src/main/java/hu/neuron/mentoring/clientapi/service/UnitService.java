package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Unit;

import java.util.List;

public interface UnitService {

    Unit findById(long id);

    Unit findByName(String name);

    List<Unit> findAll();

    void save(Unit unit);

    void delete(Unit unit);
}
