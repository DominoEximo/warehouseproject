package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Offer;
import hu.neuron.mentoring.clientapi.entity.OrderItem;
import hu.neuron.mentoring.clientapi.service.OrderService;
import hu.neuron.mentoring.core.dao.OrderDAO;
import org.hibernate.type.OrderedMapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    @Override
    public OrderItem findById(long id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderDAO.getAll();
    }

    @Override
    public void save(OrderItem orderItem) {
        orderDAO.save(orderItem);
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderDAO.delete(orderItem.getId());
    }

    @Override
    public void update(OrderItem orderItem) {
        orderDAO.update(orderItem);
    }

    @Override
    public List<OrderItem> findAllPaginated(int pageNumber, int pageSize) {
        return orderDAO.findAllPaginated(pageNumber,pageSize);
    }

    @Override
    public List<OrderItem> findAllByStatus(String status) {
        List<OrderItem> orders = findAll();
        List<OrderItem> filtered = orders.stream().filter(o -> o.getStatus().getStatusName().equals(status)).collect(Collectors.toList());
        return filtered;
    }
}
