package hu.neuron.mentoring.clientapi.repositories;

import hu.neuron.mentoring.clientapi.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UnitRepository extends JpaRepository<Unit,Long> {

    List<Unit> findByUnitName(String name);
}
