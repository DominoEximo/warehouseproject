package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OfferRepository extends PagingAndSortingRepository<Offer,Long>,JpaRepository<Offer,Long>{
}
