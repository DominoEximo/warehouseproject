package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
