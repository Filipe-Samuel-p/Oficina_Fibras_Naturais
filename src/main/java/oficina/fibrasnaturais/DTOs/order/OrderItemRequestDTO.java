package oficina.fibrasnaturais.DTOs.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO responsável por capturar os dados de um item específico dentro do carrinho.
 * * MOTIVO DO DESIGN:
 * 1. Apenas ID e Quantidade: O front-end JAMAIS deve enviar o preço.
 * Se o front enviasse o preço, um usuário malicioso poderia alterar o JSON
 * e comprar um produto de R$ 100,00 por R$ 1,00. O preço deve ser buscado
 * no banco de dados pelo ID.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    private Long productId;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima é 1")
    private Integer quantity;
}
