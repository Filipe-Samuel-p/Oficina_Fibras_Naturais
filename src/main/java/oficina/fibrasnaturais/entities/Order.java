package oficina.fibrasnaturais.entities;

import jakarta.persistence.*;
import lombok.*;
import oficina.fibrasnaturais.enums.OrderStatus;
import oficina.fibrasnaturais.id.NanoId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @Id
    @NanoId
    @Column(length = 10)
    @EqualsAndHashCode.Include
    private String id;

    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalValue;

    @Column(name = "address_snapshot", columnDefinition = "TEXT")
    private String addressSnapshot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
}
