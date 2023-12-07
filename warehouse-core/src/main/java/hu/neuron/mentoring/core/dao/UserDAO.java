package hu.neuron.mentoring.core.dao;


import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.core.repositories.RoleRepository;
import hu.neuron.mentoring.core.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO implements DAO<User>{

    @Autowired
    EntityManagerFactory emf;
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public UserDAO() {
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }

    public void setUpMockedData(){
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleRepository.findByName("USER"));
        List<Role> bacofficeRoles = new ArrayList<>();
        bacofficeRoles.add(roleRepository.findByName("USER"));
        bacofficeRoles.add(roleRepository.findByName("BACKOFFICE"));
        save(new User("user","test@gmail.com","06306618148","2002/04/05",'m',"password",userRoles));
        save(new User("admin","DominoEximo@gmail.com","0630123502","2010/10/12",'f',"password",bacofficeRoles));

    }
}
