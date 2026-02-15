package oficina.fibrasnaturais.DTOs.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oficina.fibrasnaturais.entities.Product;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String name;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String description;

    @NotNull(message = "Preço por unidade não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Preço por unidade deve ser maior que zero")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Quantidade em estoque não pode ser nula")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    private Integer stockQuantity;

    @NotBlank(message = "URL da imagem não pode ser vazia")
    private String imageUrl;

    @NotNull(message = "Status ativo não pode ser nulo")
    private Boolean active;


    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        pricePerUnit = entity.getPricePerUnit();
        stockQuantity = entity.getStockQuantity();
        imageUrl = entity.getImageUrl();
        active = entity.getActive();
    }
}