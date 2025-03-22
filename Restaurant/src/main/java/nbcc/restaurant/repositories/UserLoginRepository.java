package nbcc.restaurant.repositories;

import nbcc.restaurant.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, String> {
}
