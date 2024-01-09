package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Offer;

import java.util.List;

public interface OfferService {

    Offer findById(long id);
    List<Offer> findAll();

    void save(Offer offer);

    void delete(Offer offer);

    void update(Offer offer);

    List<Offer> findAllPaginated(int pageNumber, int pageSize);

    List<Offer> findAllByProductCategory(String category);
}
