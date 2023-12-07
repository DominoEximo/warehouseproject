package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.core.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Override
    public Role findById(long id) {
        return roleDAO.findById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleDAO.getAll();
    }

    @Override
    public void save(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void delete(Role role) {
        roleDAO.delete(role.getId());
    }

    @Override
    public void setUpMockedData() {
        roleDAO.setUpMockedData();
    }
}
