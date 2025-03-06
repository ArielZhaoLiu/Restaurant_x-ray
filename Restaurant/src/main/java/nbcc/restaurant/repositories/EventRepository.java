package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
