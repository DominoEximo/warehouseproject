package hu.neuron.mentoring.core.dao;

import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.core.repositories.RoleRepository;
import hu.neuron.mentoring.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO implements DAO<User>{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
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


    public void setUpMockedData(){
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleRepository.findByName("USER"));
        List<Role> bacofficeRoles = new ArrayList<>();
        bacofficeRoles.add(roleRepository.findByName("BACKOFFICE"));
        save(new User("David","test@gmail.com","06306618148",new Date(2002,4,5),'m',"password",userRoles));
        save(new User("Domino","DominoEximo@gmail.com","0630123502",new Date(2012,4,5),'f',"betterpassword",bacofficeRoles));

    }
}
