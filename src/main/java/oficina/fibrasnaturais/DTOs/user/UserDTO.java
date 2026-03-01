package oficina.fibrasnaturais.DTOs.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oficina.fibrasnaturais.entities.User;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @NotBlank(message = "Esse campo não pode ser nulo ou apenas espaço em branco")
    private String name;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Senha não pode ser vazia")
    private String password;

    @NotBlank(message = "Telefone não pode ser vazio")
    private String phone;
    @Valid
    private AddressDTO address;

    public UserDTO(User entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        phone = entity.getPhone();
        if (entity.getAddress() != null) {
            address = new AddressDTO(entity.getAddress());
        }
    }
}
