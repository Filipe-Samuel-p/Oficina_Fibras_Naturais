package oficina.fibrasnaturais.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "tb_shop_config")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

}