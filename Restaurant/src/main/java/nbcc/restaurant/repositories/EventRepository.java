package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByMenuId(Long menuId);
    List<Event> findByStartDateGreaterThanEqual(LocalDate startDate);
    List<Event> findByEndDateLessThanEqual(LocalDate endDate);
    List<Event> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);
}
