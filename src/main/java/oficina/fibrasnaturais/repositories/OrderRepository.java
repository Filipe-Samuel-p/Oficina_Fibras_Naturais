package oficina.fibrasnaturais.repositories;

import oficina.fibrasnaturais.entities.Order;
import oficina.fibrasnaturais.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUser(User user);
}
