package oficina.fibrasnaturais.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oficina.fibrasnaturais.enums.RoleName;


@Entity
@Table(name = "tb_role")
@Getter
@Setter
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName authority;

}