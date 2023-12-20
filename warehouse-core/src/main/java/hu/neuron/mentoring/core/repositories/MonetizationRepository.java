package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Monetization;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MonetizationRepository extends PagingAndSortingRepository<Monetization,Long>,JpaRepository<Monetization,Long> {

    List<Monetization> getAll(PageRequest pageRequest);
}
