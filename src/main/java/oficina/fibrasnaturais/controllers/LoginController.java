package oficina.fibrasnaturais.controllers;

import oficina.fibrasnaturais.DTOs.loginDTO.LoginRequestDTO;
import oficina.fibrasnaturais.DTOs.loginDTO.LoginResponseDTO;
import oficina.fibrasnaturais.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
public class LoginController {


    private AuthService service;

    public LoginController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){
        var response = service.login(dto);
        return ResponseEntity.ok(response);
    }

}
