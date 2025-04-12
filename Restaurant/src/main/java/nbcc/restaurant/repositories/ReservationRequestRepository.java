package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.DiningTable;
import nbcc.restaurant.entities.ReservationRequest;
import nbcc.restaurant.entities.ReservationStatus;
import nbcc.restaurant.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    List<ReservationRequest> findBySeating_Event_IdOrderBySeating_SeatingDateTimeAsc(Long eventId);
    List<ReservationRequest> findByStatusOrderBySeating_SeatingDateTimeAsc(ReservationStatus status);
    List<ReservationRequest> findBySeating_Event_IdAndStatusOrderBySeating_SeatingDateTimeAsc(long seatingEventId, ReservationStatus status);
    List<ReservationRequest> findAllByOrderBySeating_SeatingDateTimeAsc();
    ReservationRequest findByAssignedTable(DiningTable table);
    List<ReservationRequest> findBySeating_Event_Id(Long eventId);
    ReservationRequest findBySeatingId(long seatingId);

    @Query("SELECT r FROM ReservationRequest r WHERE r.seating IN :seatings AND r.status = :status ")
    List<ReservationRequest> findApprovedReservationsBySeating(@Param("seatings") List<Seating> seatings,
                                                               @Param("status") ReservationStatus status);
}
