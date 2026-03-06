package oficina.fibrasnaturais.DTOs.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String name;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Pattern(
            regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}",
            message = "O telefone precisa usar o formato (XX) XXXXX-XXXX"
    )
    private String phone;

    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String password;
}