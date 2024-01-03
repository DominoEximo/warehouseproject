package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Monetization;

import java.util.List;

public interface MonetizationService {

    Monetization findById(long id);
    List<Monetization> findAll();

    void save(Monetization monetization);

    void delete(Monetization monetization);

    void update(Monetization monetization);

    List<Monetization> findAllPaginated(int pageNumber, int pageSize);
}
