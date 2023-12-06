package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.clientapi.service.UnitService;
import hu.neuron.mentoring.core.dao.UnitDAO;
import hu.neuron.mentoring.core.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitDAO unitDAO;

    @Override
    public Unit findById(long id) {
        return unitDAO.findById(id);
    }

    @Override
    public Unit findByName(String name) {
        return unitDAO.findByName(name);
    }

    @Override
    public List<Unit> findAll() {
        return unitDAO.getAll();
    }

    @Override
    public void save(Unit unit) {
        unitDAO.save(unit);
    }

    @Override
    public void delete(Unit unit) {
        unitDAO.delete(unit.getId());
    }
}
