package oficina.fibrasnaturais.controllers;


import oficina.fibrasnaturais.DTOs.User.UserDTO;
import oficina.fibrasnaturais.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAllRoles('CLIENT')")
    public ResponseEntity<UserDTO> getUserProfile (JwtAuthenticationToken token){

        System.out.println("ENTROU NA FUNCAO");
        var userDTO = service.getUserProfile(token);

        System.out.println("RETORNOU DA FUNCAO");



        return ResponseEntity.ok(userDTO);
    }
}
