package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatingRepository extends JpaRepository<Seating, Long> {

    List<Seating> findByEventId(Long event_id);
    Seating findById(long id);
}
