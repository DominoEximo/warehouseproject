package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonetizationRepository extends PagingAndSortingRepository<Monetization,Long>,JpaRepository<Monetization,Long> {


}
