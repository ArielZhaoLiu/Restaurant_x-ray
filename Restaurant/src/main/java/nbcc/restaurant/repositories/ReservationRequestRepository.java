package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.DiningTable;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    List<ReservationRequest> findBySeating_Event_IdOrderBySeating_SeatingDateTimeAsc(Long eventId);
    List<ReservationRequest> findByStatusOrderBySeating_SeatingDateTimeAsc(ReservationStatus status);
    List<ReservationRequest> findBySeating_Event_IdAndStatusOrderBySeating_SeatingDateTimeAsc(long seatingEventId, ReservationStatus status);
    List<ReservationRequest> findAllByOrderBySeating_SeatingDateTimeAsc();

    List<ReservationRequest> findBySeating_Event_Id(Long eventId);

    ReservationRequest findByAssignedTable(DiningTable assignedTable);

}
