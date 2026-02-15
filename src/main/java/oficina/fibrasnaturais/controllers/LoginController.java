package oficina.fibrasnaturais.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import oficina.fibrasnaturais.DTOs.user.UserDTO;
import oficina.fibrasnaturais.DTOs.login.LoginRequestDTO;
import oficina.fibrasnaturais.DTOs.login.LoginResponseDTO;
import oficina.fibrasnaturais.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Autenticação e Registro", description = "Operações para login e registro de novos usuários")
@RestController
@RequestMapping("api/v1/auth")
public class LoginController {


    private AuthService service;

    public LoginController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna um token JWT")
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){
        var response = service.login(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registro de novo cliente", description = "Registra um novo cliente no sistema. **O campo 'address' não deve ser fornecido durante o registro inicial; ele é preenchido posteriormente.**")
    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> newClient (@RequestBody @Valid UserDTO dto){
        dto = service.newClient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
