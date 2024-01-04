package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Order;

import java.util.List;

public interface OrderService {

    Order findById(long id);
    List<Order> findAll();

    void save(Order order);

    void delete(Order order);

    void update(Order order);

    List<Order> findAllPaginated(int pageNumber, int pageSize);


}
