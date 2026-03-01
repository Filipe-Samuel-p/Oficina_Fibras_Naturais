package oficina.fibrasnaturais.DTOs.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO de entrada para criação de um novo pedido.
 * * MOTIVO DO DESIGN:
 * 1. Lista de Itens: Recebe uma lista da classe auxiliar OrderItemRequestDTO.
 * 2. AddressId: Recebemos apenas o ID do endereço (Long), e não o objeto endereço completo.
 * Isso garante que o usuário só pode usar um endereço que já esteja validado
 * e salvo no perfil dele, evitando dados inconsistentes ou endereços falsos digitados na hora.
 * 3. Sem UserID: Não pedimos o ID do usuário aqui por segurança. O ID do usuário
 * será extraído automaticamente do Token JWT no Controller.
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    @NotEmpty(message = "O carrinho não pode estar vazio")
    private List<OrderItemRequestDTO> items;

    @NotNull(message = "É necessário selecionar um endereço de entrega")
    private Long addressId;
}
