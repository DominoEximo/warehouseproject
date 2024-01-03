package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.clientapi.service.MonetizationService;
import hu.neuron.mentoring.core.dao.MonetizationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonetizationServiceImpl implements MonetizationService {

    @Autowired
    MonetizationDAO monetizationDAO;
    @Override
    public Monetization findById(long id) {
        return monetizationDAO.findById(id);
    }

    @Override
    public List<Monetization> findAll() {
        return monetizationDAO.getAll();
    }

    @Override
    public void save(Monetization monetization) {
        monetizationDAO.save(monetization);
    }

    @Override
    public void delete(Monetization monetization) {
        monetizationDAO.delete(monetization.getId());
    }

    @Override
    public void update(Monetization monetization) {
        monetizationDAO.save(monetization);
    }

    @Override
    public List<Monetization> findAllPaginated(int pageNumber, int pageSize) {
        return monetizationDAO.getAllPaginated(pageNumber,pageSize);
    }
}
