package oficina.fibrasnaturais.controllers;

import oficina.fibrasnaturais.DTOs.user.UserDTO;
import oficina.fibrasnaturais.DTOs.login.LoginRequestDTO;
import oficina.fibrasnaturais.DTOs.login.LoginResponseDTO;
import oficina.fibrasnaturais.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/auth")
public class LoginController {


    private AuthService service;

    public LoginController(AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){
        var response = service.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> newClient (@RequestBody UserDTO dto){
        dto = service.newClient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
