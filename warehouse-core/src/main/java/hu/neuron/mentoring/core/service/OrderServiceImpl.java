package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Order;
import hu.neuron.mentoring.clientapi.service.OrderService;
import hu.neuron.mentoring.core.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    @Override
    public Order findById(long id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.getAll();
    }

    @Override
    public void save(Order order) {
        orderDAO.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDAO.delete(order.getId());
    }

    @Override
    public void update(Order order) {
        orderDAO.update(order);
    }

    @Override
    public List<Order> findAllPaginated(int pageNumber, int pageSize) {
        return orderDAO.findAllPaginated(pageNumber,pageSize);
    }
}
