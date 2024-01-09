package hu.neuron.mentoring.core.dao;



import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.core.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitDAO implements DAO<Unit>{


    @Autowired
    UnitRepository unitRepository;

    public UnitDAO() {
    }

    @Override
    public Unit findById(long id) {
        return unitRepository.findById(id).get();
    }


    public Unit findByName(String name){
        return unitRepository.findByUnitName(name);
    }


    @Override
    public List<Unit> getAll() {

        return unitRepository.findAll();
    }

    @Override
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public Unit update(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public void delete(long id) {
        unitRepository.deleteById(id);
    }

    public void setUpMockedData(){
        save(new Unit("kg"));
        save(new Unit("lbs"));
    }
}
