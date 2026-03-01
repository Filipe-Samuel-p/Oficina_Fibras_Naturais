package oficina.fibrasnaturais.DTOs.shopConfig_admin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopConfigDTO {

    @NotBlank(message = "O número do WhatsApp é obrigatório")
    private String whatsappNumber;

}