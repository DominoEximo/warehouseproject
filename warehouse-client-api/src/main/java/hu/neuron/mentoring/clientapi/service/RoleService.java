package hu.neuron.mentoring.clientapi.service;

import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;

import java.util.List;

public interface RoleService {

    Role findById(long id);

    Role findByName(String name);

    List<Role> findAll();

    void save(Role role);

    void delete(Role role);

    /**
     * adds predefined roles to the database
     */
    void setUpMockedData();
}
