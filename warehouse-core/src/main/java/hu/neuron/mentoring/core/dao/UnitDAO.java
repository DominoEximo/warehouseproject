package hu.neuron.mentoring.core.dao;


import hu.neuron.mentoring.clientapi.datasource.DatasourceConfig;
import hu.neuron.mentoring.clientapi.entity.Unit;
import hu.neuron.mentoring.core.repositories.UnitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitDAO implements DAO<Unit>{

    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @Autowired
    UnitRepository unitRepository;

    public UnitDAO() {
    }


    public Unit findById(long id) {
        return em.find(Unit.class,id);
    }


    public Unit findByName(String name){
        return unitRepository.findByUnitName(name);
    }

    @Override
    public Unit findById(int id) {
        return null;
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
        return em.merge(unit);
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
