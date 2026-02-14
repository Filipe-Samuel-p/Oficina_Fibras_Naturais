package oficina.fibrasnaturais.DTOs.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para exibir os detalhes de um item processado.
 * * MOTIVO DO DESIGN:
 * 1. ProductName: Diferente da requisição (que só tem ID), na resposta precisamos
 * enviar o NOME do produto para o front-end exibir na tela de confirmação.
 * 2. Price (Snapshot): Este preço é o valor COBRADO no momento da compra.
 * Mesmo que o produto suba de preço amanhã, este DTO mostrará quanto custava
 * no segundo exato em que o pedido foi fechado.
 * 3. SubTotal: Calculado no back-end (Preço * Qtd) para facilitar a vida do front-end.
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPriceOrderItem;
}