package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {
}
