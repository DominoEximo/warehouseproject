package hu.neuron.mentoring.web.beans;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Role;
import hu.neuron.mentoring.clientapi.entity.User;
import hu.neuron.mentoring.clientapi.service.RoleService;
import hu.neuron.mentoring.clientapi.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
public class UserBean implements Serializable {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    private List<User> users;

    private List<String> roles;

    private int page = 1;

    private int length = 5;

    @PostConstruct
    public void init(){
        roles = roleService.findAll().stream().map(Role::getName).collect(Collectors.toList());
        loadUsers();
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

    public void loadUsers(){
        users = null;
        users = userService.getAllPaginated(page,length);
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
}
