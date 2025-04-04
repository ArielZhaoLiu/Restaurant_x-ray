package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Menu;
import nbcc.restaurant.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
