package oficina.fibrasnaturais.controllers;

import oficina.fibrasnaturais.services.CoordinatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coordinator")

@PreAuthorize("hasRole('COORDINATOR')")
public class CoordinatorController {

    private final CoordinatorService service;

    public CoordinatorController(CoordinatorService service) {
        this.service = service;
    }

    @DeleteMapping("/admins/{adminID}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable UUID adminID, JwtAuthenticationToken token) {

        var currentUserId = UUID.fromString(token.getName());

        service.deleteAdmin(adminID, currentUserId);

        return ResponseEntity.noContent().build();
    }
}