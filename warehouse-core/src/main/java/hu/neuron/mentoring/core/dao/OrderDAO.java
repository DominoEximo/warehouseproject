package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.OrderItem;
import hu.neuron.mentoring.core.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO implements DAO<OrderItem>{

    @Autowired
    OrderRepository orderRepository;

    public OrderDAO() {
        //Constructor
    }

    @Override
    public OrderItem findById(long id) {

        Optional<OrderItem> found = orderRepository.findById(id);
        OrderItem orderItem = new OrderItem();

        if(found.isPresent()){
            orderItem.setId(found.get().getId());
            orderItem.setStatus(found.get().getStatus());
            orderItem.setAddress(found.get().getAddress());
            orderItem.setUser(found.get().getUser());
            orderItem.setPaymentOption(found.get().getPaymentOption());
            orderItem.setShippingOption(found.get().getShippingOption());
            orderItem.setOrderedItems(found.get().getOrderedItems());
        }

        return orderItem;
    }

    @Override
    public List<OrderItem> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(OrderItem orderItem) {
        orderRepository.save(orderItem);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return orderRepository.save(orderItem);
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderItem> findAllPaginated(int pageNumber, int pageSize){
        return orderRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }
}
