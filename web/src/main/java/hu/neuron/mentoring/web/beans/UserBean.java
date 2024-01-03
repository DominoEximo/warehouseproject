package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class UserBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(UserBean.class);


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    private List<User> users;

    private User userToBeManaged;

    private List<String> roles;

    private List<Role> roleList;

    private int page = 1;

    private int length = 5;

    private String chosenRole;

    @PostConstruct
    public void init(){
        try {
            userToBeManaged = new User();
            roles = roleService.findAll().stream().map(Role::getName).collect(Collectors.toList());
            loadUsers();
        }catch (Exception e){
            logger.error("Error during bean initialization", e);
        }

    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void loadUsers(){
        String transactionName = "loadUsers";
        try {
            logger.info("Transaction '{}' started in FormProcessBean", transactionName);
            users = null;
            users = userService.getAllPaginated(page,length);
            logger.info("Transaction '{}' completed successfully in FormProcessBean", transactionName);
        }catch (Exception e){
            logger.error("Transaction '{}' failed in FormProcessBean: {}",transactionName,e.getMessage());
        }

    }

    public void nextPage(){
        if(userService.getAllPaginated(page,length).size() / length >= page){
            page += 1;
            loadUsers();
        }
    }
    public void prevPage(){
        if(page > 1){
            page -= 1;
            loadUsers();
        }

    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getChosenRole() {
        return chosenRole;
    }

    public void setChosenRole(String chosenRole) {
        this.chosenRole = chosenRole;
    }

    public User getUserToBeManaged() {
        return userToBeManaged;
    }

    public void setUserToBeManaged(User userToBeManaged) {
        this.userToBeManaged = userToBeManaged;
    }

    public void setUpUserToBeManaged(User user){
        userToBeManaged.setId(user.getId());
        userToBeManaged.setRoles(user.getRoles());
        userToBeManaged.setEmail(user.getEmail());
        userToBeManaged.setPassword(user.getPassword());
        userToBeManaged.setPhoneNumber(user.getPhoneNumber());
        userToBeManaged.setName(user.getName());
        userToBeManaged.setBirthDate(user.getBirthDate());
        userToBeManaged.setGender(user.getGender());
    }

    public void listAvailableRoles(User user){
        setUpUserToBeManaged(user);
        List<Role> userRoleList = user.getRoles();
        List<String> names = userRoleList.stream().map(u -> u.getName()).collect(Collectors.toList());

        roleList = roleService.findAll();
        roleList = roleList.stream().filter(r -> !names.contains(r.getName())).collect(Collectors.toList());

    }

    public void removeRoleFromUser(String role){
        List<Role> roles = userToBeManaged.getRoles();
        for (Role R : roles){
            if (R.getName().equals(role)){
                roles.remove(R);
            }
        }
        userToBeManaged.setRoles(roles);
        userService.save(userToBeManaged);
        userToBeManaged = new User();
    }

    public void addRoleToUser(String role){
        List<Role> roles = userToBeManaged.getRoles();
        roles.add(roleService.findByName(role));
        userToBeManaged.setRoles(roles);
        userService.save(userToBeManaged);
        userToBeManaged = new User();
    }
}
