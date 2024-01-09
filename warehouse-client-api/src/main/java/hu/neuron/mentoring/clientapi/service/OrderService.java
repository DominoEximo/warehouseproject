package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.OrderItem;

import java.util.List;

public interface OrderService {

    OrderItem findById(long id);
    List<OrderItem> findAll();

    void save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    void update(OrderItem orderItem);

    List<OrderItem> findAllPaginated(int pageNumber, int pageSize);

    List<OrderItem> findAllByStatus(String status);


}
