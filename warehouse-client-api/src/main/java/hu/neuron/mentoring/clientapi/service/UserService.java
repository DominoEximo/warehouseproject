package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByName(String name);
    List<User> findAll();

    void save(User user);

    void delete(User user);

    /**
     * adds predefined users to the database
     */
    void setUpMockedData();
}
