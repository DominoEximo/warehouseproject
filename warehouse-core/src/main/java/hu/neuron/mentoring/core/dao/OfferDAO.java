package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.core.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Component
public class OfferDAO implements Serializable, DAO<Offer>{

    @Autowired
    OfferRepository offerRepository;

    public OfferDAO() {
        //Constructor
    }

    @Override
    public Offer findById(long id) {
        Optional<Offer> found = offerRepository.findById(id);
        Offer offer = new Offer();

        if(found.isPresent()){
            offer.setId(found.get().getId());
            offer.setPrice(found.get().getPrice());
            offer.setProduct(found.get().getProduct());
            offer.setStartDate(found.get().getStartDate());
            offer.setEndDate(found.get().getEndDate());
        }

        return offer;
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public void delete(long id) {
        offerRepository.deleteById(id);
    }

    public List<Offer> getAllPaginated(int pageNumber, int pageSize) {
        return offerRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }
}
