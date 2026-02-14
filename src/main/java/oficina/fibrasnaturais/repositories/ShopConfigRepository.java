package oficina.fibrasnaturais.repositories;

import oficina.fibrasnaturais.entities.ShopConfig;
import oficina.fibrasnaturais.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopConfigRepository extends JpaRepository<ShopConfig, Long> {

    @Query(value = "SELECT * FROM tb_shop_config LIMIT 1", nativeQuery = true)
    Optional<ShopConfig> findMainConfig();


}
