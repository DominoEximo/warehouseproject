package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Status;
import hu.neuron.mentoring.clientapi.service.StatusService;
import hu.neuron.mentoring.core.dao.StatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusServieceImpl implements StatusService {

    @Autowired
    StatusDAO statusDAO;

    @Override
    public Status findById(long id) {
        return statusDAO.findById(id);
    }

    @Override
    public Status findByName(String name) {
        return statusDAO.findByName(name);
    }

    @Override
    public List<Status> findAll() {
        return statusDAO.getAll();
    }

    @Override
    public void save(Status status) {
        statusDAO.save(status);
    }

    @Override
    public void delete(Status status) {
        statusDAO.delete(status.getId());
    }

    @Override
    public void setUpMockedData() {
        statusDAO.setUpMockedData();
    }
}
