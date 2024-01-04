package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Status;
import hu.neuron.mentoring.core.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StatusDAO implements DAO<Status>{

    @Autowired
    StatusRepository statusRepository;

    public StatusDAO() {
    }

    @Override
    public Status findById(long id) {
        Optional<Status> found = statusRepository.findById(id);
        Status status = new Status();

        if(found.isPresent()){
            status.setId(found.get().getId());
            status.setStatusName(found.get().getStatusName());
        }

        return status;
    }

    @Override
    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    @Override
    public void save(Status status) {
        statusRepository.save(status);
    }

    @Override
    public Status update(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public void delete(long id) {
        statusRepository.deleteById(id);
    }

    public Status findByName(String name){
        return statusRepository.findByStatusName(name);
    }

    public void setUpMockedData(){
        save(new Status("Pending"));
        save(new Status("Shipped"));
        save(new Status("Canceled"));
    }
}
