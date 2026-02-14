package oficina.fibrasnaturais.DTOs.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oficina.fibrasnaturais.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO de saída com o resumo completo do pedido gerado.
 * * MOTIVO DO DESIGN:
 * 1. String ID (NanoID): Retorna o código legível (ex: "K9X2-M4B9") para exibição.
 * 2. DeliveryAddressSnapshot: Retorna o texto formatado do endereço (Rua X, nº Y...)
 * para que o usuário veja onde será entregue, sem precisar fazer outra consulta ao banco.
 * 3. WhatsappLink: O campo mais importante para o seu projeto. O Front-end vai ler
 * esta String e redirecionar o navegador. Colocamos aqui para que a lógica de
 * geração do link fique centralizada no servidor.
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private String id;
    private Instant moment;
    private OrderStatus status;
    private BigDecimal total;
    private String addressSnapshot;
    private List<OrderItemResponseDTO> items;
    private String whatsappLink;
}
