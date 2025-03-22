package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail getUserDetailByUsername(String username);

    boolean existsByUsername(String username);
}
