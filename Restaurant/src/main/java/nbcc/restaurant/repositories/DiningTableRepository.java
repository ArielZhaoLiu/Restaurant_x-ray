package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    List<DiningTable> findByLayoutId(Long layoutId);
}
