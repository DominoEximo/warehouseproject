package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByStatusName(String name);
}
