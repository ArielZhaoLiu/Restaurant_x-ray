package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    List<ReservationRequest> findBySeating_Event_Id(Long eventId);
    List<ReservationRequest> findByStatus(ReservationStatus status);
    List<ReservationRequest> findBySeating_Event_IdAndStatus(long seatingEventId, ReservationStatus status);

}
