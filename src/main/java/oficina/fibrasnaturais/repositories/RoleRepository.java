package oficina.fibrasnaturais.repositories;

import oficina.fibrasnaturais.entities.Role;
import oficina.fibrasnaturais.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByAuthority(RoleName authority);
}
