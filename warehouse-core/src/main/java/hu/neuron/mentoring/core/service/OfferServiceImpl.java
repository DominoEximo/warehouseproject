package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.service.OfferService;
import hu.neuron.mentoring.core.dao.OfferDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService{

    @Autowired
    OfferDAO offerDAO;
    @Override
    public Offer findById(long id) {
        return offerDAO.findById(id);
    }

    @Override
    public List<Offer> findAll() {
        return offerDAO.getAll();
    }

    @Override
    public void save(Offer offer) {
        offerDAO.save(offer);
    }

    @Override
    public void delete(Offer offer) {
        offerDAO.delete(offer.getId());
    }

    @Override
    public void update(Offer offer) {
        offerDAO.update(offer);
    }

    @Override
    public List<Offer> findAllPaginated(int pageNumber, int pageSize) {
        return offerDAO.getAllPaginated(pageNumber,pageSize);
    }

    @Override
    public List<Offer> findAllByProductCategory(String category) {
        List<Offer> offers = findAll();
        List<Offer> filtered = offers.stream().filter(o -> o.getProduct().getCategory().getCategoryName().equals(category)).collect(Collectors.toList());
        return filtered;
    }
}
