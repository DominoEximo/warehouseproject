package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.Order;
import hu.neuron.mentoring.core.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO implements DAO<Order>{

    @Autowired
    OrderRepository orderRepository;

    public OrderDAO() {
    }

    @Override
    public Order findById(long id) {

        Optional<Order> found = orderRepository.findById(id);
        Order order = new Order();

        if(found.isPresent()){
            order.setId(found.get().getId());
            order.setStatus(found.get().getStatus());
            order.setAddress(found.get().getAddress());
            order.setUser(found.get().getUser());
            order.setPaymentOption(found.get().getPaymentOption());
            order.setShippingOption(found.get().getShippingOption());
            order.setOrderedItems(found.get().getOrderedItems());
        }

        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findAllPaginated(int pageNumber, int pageSize){
        return orderRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }
}
