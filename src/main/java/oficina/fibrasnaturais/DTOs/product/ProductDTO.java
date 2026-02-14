package oficina.fibrasnaturais.DTOs.product;

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

    private String name;

    private String description;

    private BigDecimal pricePerUnit;

    private Integer stockQuantity;

    private String imageUrl;
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