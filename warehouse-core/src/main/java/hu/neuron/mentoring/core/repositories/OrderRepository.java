package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderItem,Long>,JpaRepository<OrderItem,Long> {
}
