package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.core.repositories.MonetizationRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class MonetizationDAO implements Serializable, DAO<Monetization>{

    @Autowired
    MonetizationRepository monetizationRepository;
    @Override
    public Monetization findById(long id) {
        return monetizationRepository.findById(id).get();
    }

    @Override
    public List<Monetization> getAll() {
        return monetizationRepository.findAll();
    }

    @Override
    public void save(Monetization monetization) {
        monetizationRepository.save(monetization);
    }

    @Override
    public Monetization update(Monetization monetization) {
        return monetizationRepository.save(monetization);
    }

    @Override
    public void delete(long id) {
        monetizationRepository.deleteById(id);
    }

    public List<Monetization> getAllPaginated(int pageNumber, int pageSize) {
        return monetizationRepository.getAll(PageRequest.of(pageNumber-1,pageSize));
    }
}
