package oficina.fibrasnaturais.DTOs.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotNull(message = "Email não pode ser nulo")
        @Email(message = "Email inválido")
        String email,

        @NotNull(message = "Senha não pode ser nula")
        String password) {
}
