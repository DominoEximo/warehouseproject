package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment,Long> {
}
