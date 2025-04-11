package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
