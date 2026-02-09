package oficina.fibrasnaturais.controllers;


import oficina.fibrasnaturais.DTOs.User.AddressDTO;
import oficina.fibrasnaturais.DTOs.User.UserDTO;
import oficina.fibrasnaturais.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
        var userDTO = service.getUserProfile(token);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(value = "/address")
    @PreAuthorize("hasAllRoles('CLIENT')")
    public ResponseEntity<AddressDTO> addAddress (@RequestBody AddressDTO dto, JwtAuthenticationToken token){
        dto = service.addAddress(dto, token);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PatchMapping("/address")
    @PreAuthorize("hasAllRoles('CLIENT')")
    public ResponseEntity<AddressDTO> updateAddress (@RequestBody AddressDTO dto, JwtAuthenticationToken token){
        AddressDTO updatedDto = service.updateAddress(dto, token);
        return ResponseEntity.ok(updatedDto);
    }
}
