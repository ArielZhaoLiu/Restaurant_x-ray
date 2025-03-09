package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatingRepository extends JpaRepository<Seating, Long> {
}
