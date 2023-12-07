package hu.neuron.mentoring.core.service;

import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.UserService;
import hu.neuron.mentoring.core.dao.RoleDAO;
import hu.neuron.mentoring.core.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User findById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    @Override
    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return (List<User>) userDAO.getAllPaginated(pageNumber,pageSize);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user.getId());
    }

    @Override
    public void setUpMockedData() {
        userDAO.setUpMockedData();
    }
}
