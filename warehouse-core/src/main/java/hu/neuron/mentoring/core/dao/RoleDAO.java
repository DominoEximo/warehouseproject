package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.core.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDAO implements DAO<Role>{

    @Autowired
    RoleRepository roleRepository;

    public RoleDAO() {
        //Constructor
    }

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    public void setUpMockedData(){
        save(new Role("BACKOFFICE"));
        save(new Role("USER"));
    }
}
