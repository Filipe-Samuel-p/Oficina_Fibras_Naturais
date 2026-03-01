package oficina.fibrasnaturais.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import oficina.fibrasnaturais.DTOs.shopConfig_admin.AdminDTO;
import oficina.fibrasnaturais.DTOs.shopConfig_admin.ShopConfigDTO;
import oficina.fibrasnaturais.services.ShopConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Configurações da Loja e Administração", description = "Operações para gerenciar configurações da loja e usuários administradores")
@RestController
@RequestMapping("/api/v1/settings")

@PreAuthorize("hasAnyRole('ADMIN') or hasRole('COORDINATOR')")
public class ShopConfigController {

    private final ShopConfigService service;

    public ShopConfigController(ShopConfigService service) {
        this.service = service;
    }


    @Operation(summary = "Obter configurações da loja", description = "Recupera as configurações gerais da loja")
    @GetMapping
    public ResponseEntity<ShopConfigDTO> getConfig() {
        return ResponseEntity.ok(service.getMainConfig());
    }

    @Operation(summary = "Atualizar configurações da loja", description = "Atualiza as configurações gerais da loja (requer perfil ADMIN ou COORDENADOR)")
    @PutMapping
    public ResponseEntity<ShopConfigDTO> updateConfig(@RequestBody @Valid ShopConfigDTO dto) {
        return ResponseEntity.ok(service.updateConfig(dto));
    }


    @Operation(summary = "Convidar novo administrador", description = "Registra um novo usuário com perfil de administrador através de um convite (requer perfil ADMIN ou COORDENADOR)")
    @PostMapping("/invite-admin")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody @Valid AdminDTO dto) {
        dto = service.registerNewAdmin(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }


    @Operation(summary = "Listar todos os administradores", description = "Recupera uma lista de todos os usuários com perfil de administrador")
    @GetMapping(value = "/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins (){
        var allAdmins = service.getAllAdmins();
        return ResponseEntity.ok(allAdmins);
    }
}