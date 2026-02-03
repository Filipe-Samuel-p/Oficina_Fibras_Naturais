package oficina.fibrasnaturais.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import oficina.fibrasnaturais.id.NanoId;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_order")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("deprecation")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @NanoId
    @Column(length = 10)
    private String id;

    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    private oficina.fibrasnaturais.enums.OrderStatus status;

    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
