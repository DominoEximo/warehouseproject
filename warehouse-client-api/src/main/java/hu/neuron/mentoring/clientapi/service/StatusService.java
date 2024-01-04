package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Status;

import java.util.List;

public interface StatusService{

    Status findById(long id);

    Status findByName(String name);

    List<Status> findAll();

    void save(Status status);

    void delete(Status status);

    /**
     * adds predefined statuses to the database
     */
    void setUpMockedData();

}
