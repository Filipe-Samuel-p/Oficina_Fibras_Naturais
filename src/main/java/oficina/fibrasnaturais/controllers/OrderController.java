package oficina.fibrasnaturais.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import oficina.fibrasnaturais.DTOs.order.OrderCreateDTO;
import oficina.fibrasnaturais.DTOs.order.OrderResponseDTO;
import oficina.fibrasnaturais.enums.OrderStatus;
import oficina.fibrasnaturais.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Tag(name = "Gestão de Pedidos", description = "Operações relacionadas à criação, visualização e atualização de pedidos")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @Operation(summary = "Criar um novo pedido", description = "Permite a um cliente criar um novo pedido (requer perfil CLIENTE)")
    @PostMapping
    @PreAuthorize("hasAllRoles('CLIENT')")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO dto, JwtAuthenticationToken token) {

        var response = orderService.createOrder(token, dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }


    @Operation(summary = "Listar meus pedidos", description = "Recupera a lista de pedidos feitos pelo usuário autenticado (requer perfil CLIENTE)")
    @GetMapping("/my-orders")
    @PreAuthorize("hasAllRoles('CLIENT')")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(JwtAuthenticationToken token) {
        return ResponseEntity.ok(orderService.getMyOrders(token));
    }


    @Operation(summary = "Listar todos os pedidos", description = "Recupera uma lista paginada de todos os pedidos no sistema (requer perfil ADMIN ou COORDENADOR)")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable) {
                var allOrders = orderService.getAllOrders(pageable);
                return ResponseEntity.ok(allOrders);
    }


    @Operation(summary = "Obter detalhes do pedido", description = "Recupera os detalhes de um pedido específico pelo seu ID (requer perfil CLIENTE, ADMIN ou COORDENADOR)")
    @GetMapping("/{productID}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<OrderResponseDTO> getOrderDetails(@PathVariable String productID) {
        return ResponseEntity.ok(orderService.getOrderDetails(productID));
    }

    /**
     * Body esperado: { "status": "COMPLETED" }
     */
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido específico (requer perfil ADMIN ou COORDENADOR)")
    @PatchMapping("/{productID}/status")
    @PreAuthorize("hasAnyRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<Void> updateStatus(@PathVariable String productID, @RequestBody Map<String, String> payload) {
        var statusStr = payload.get("status");
        try {
            var newStatus = OrderStatus.valueOf(statusStr.toUpperCase());
            orderService.updateStatus(productID, newStatus);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}