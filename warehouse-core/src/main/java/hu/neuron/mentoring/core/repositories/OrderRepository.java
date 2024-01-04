package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long>,JpaRepository<Order,Long> {
}
