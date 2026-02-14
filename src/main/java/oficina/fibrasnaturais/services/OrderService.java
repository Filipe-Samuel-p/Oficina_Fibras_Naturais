package oficina.fibrasnaturais.services;

import jakarta.transaction.Transactional;
import oficina.fibrasnaturais.DTOs.order.OrderCreateDTO;
import oficina.fibrasnaturais.DTOs.order.OrderItemRequestDTO;
import oficina.fibrasnaturais.DTOs.order.OrderItemResponseDTO;
import oficina.fibrasnaturais.DTOs.order.OrderResponseDTO;
import oficina.fibrasnaturais.entities.*;
import oficina.fibrasnaturais.enums.OrderStatus;
import oficina.fibrasnaturais.exceptions.ConflictException;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ShopConfigRepository shopConfigRepository;

    @Value("${app.whatsapp.number}")
    private String sellerPhone;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        AddressRepository addressRepository, UserRepository userRepository, ShopConfigRepository shopConfigRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.shopConfigRepository = shopConfigRepository;
    }


    @Transactional
    public OrderResponseDTO createOrder(JwtAuthenticationToken token, OrderCreateDTO dto) {

        var userID = UUID.fromString(token.getName());

        var user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        if (!user.getAddress().equals(address)){
            throw new ConflictException("Endereço do usuário não é o mesmo do pedido");
        }

        String addressSnapshot = String.format("%s, %s - %s, %s - CEP: %s",
                address.getStreet(), address.getNumber(), address.getNeighborhood(),
                address.getCity(), address.getZipCode());

        // 4. Iniciar Pedido
        Order order = new Order();
        order.setUser(user);
        order.setAddressSnapshot(addressSnapshot);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalOrder = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            var product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ID: " + itemDTO.getProductId()));

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new ConflictException("Estoque insuficiente para o produto: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());

            var orderItemPrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            orderItem.setTotalPriceItem(orderItemPrice); // preco total de cada item do pedido

            order.getItems().add(orderItem);

            totalOrder = totalOrder.add(orderItem.getTotalPriceItem());
        }



        order.setTotalValue(totalOrder);

        order = orderRepository.save(order);

        var sellerPhone = getSellerPhone();
        var waLink = generateWhatsAppLink(order, user, sellerPhone);

        return toDTO(order, waLink);
    }

    @Transactional
    public List<OrderResponseDTO> getMyOrders(JwtAuthenticationToken token) {

        var userID = UUID.fromString(token.getName());

        var user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return orderRepository.findByUser(user).stream()
                .map(order ->  toDTO(order, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(order -> toDTO(order, null));
    }

    @Transactional
    public OrderResponseDTO getOrderDetails(String orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + orderId));
        return toDTO(order, null);
    }

    @Transactional
    public void updateStatus(String orderId, OrderStatus newStatus) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + orderId));
        order.setStatus(newStatus);
        orderRepository.save(order);
    }


    private String generateWhatsAppLink(Order order, User user, String sellerPhone) {
        StringBuilder msg = new StringBuilder();

        msg.append("Olá! Gostaria de finalizar o pedido *#").append(order.getId()).append("*");
        msg.append("\n\n*Cliente:* ").append(user.getName());

        msg.append("\n\n*Itens do Pedido:*");
        for (OrderItem item : order.getItems()) {
            msg.append("\n- ").append(item.getQuantity()).append("x ")
                    .append(item.getProduct().getName())
                    .append(" (R$ ").append(item.getProduct().getPricePerUnit()).append(" un.)");
        }

        msg.append("\n\n*Total: R$ ").append(order.getTotalValue()).append("*");
        msg.append("\n\n*Endereço de Entrega:*\n").append(order.getAddressSnapshot());


        String encodedMsg = URLEncoder.encode(msg.toString(), StandardCharsets.UTF_8);

        return "https://wa.me/" + sellerPhone + "?text=" + encodedMsg;
    }

    private String getSellerPhone() {
        return shopConfigRepository.findMainConfig()
                .map(ShopConfig::getWhatsappNumber)
                .orElse("5522999999999");
    }

    private OrderResponseDTO toDTO(Order order, String waLink) {

        List<OrderItemResponseDTO> itemsResponse = order.getItems().stream()
                .map(i -> new OrderItemResponseDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getProduct().getPricePerUnit(),
                        i.getTotalPriceItem()
                        ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getCreationDate(),
                order.getStatus(),
                order.getTotalValue(),
                order.getAddressSnapshot(),
                itemsResponse,
                waLink
        );
    }
}