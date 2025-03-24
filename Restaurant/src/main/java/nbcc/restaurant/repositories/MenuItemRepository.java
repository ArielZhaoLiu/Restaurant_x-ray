package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByMenuId(Long menu_id);
}
