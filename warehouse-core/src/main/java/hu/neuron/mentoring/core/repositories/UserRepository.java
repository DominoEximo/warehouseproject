package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
