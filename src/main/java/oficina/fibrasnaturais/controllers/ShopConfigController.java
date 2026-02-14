package oficina.fibrasnaturais.controllers;

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

@RestController
@RequestMapping("/api/v1/settings")

@PreAuthorize("hasAnyRole('ADMIN') or hasRole('COORDINATOR')")
public class ShopConfigController {

    private final ShopConfigService service;

    public ShopConfigController(ShopConfigService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<ShopConfigDTO> getConfig() {
        return ResponseEntity.ok(service.getMainConfig());
    }

    @PutMapping
    public ResponseEntity<ShopConfigDTO> updateConfig(@RequestBody @Valid ShopConfigDTO dto) {
        return ResponseEntity.ok(service.updateConfig(dto));
    }


    @PostMapping("/invite-admin")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody @Valid AdminDTO dto) {
        dto = service.registerNewAdmin(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @GetMapping(value = "/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins (){
        var allAdmins = service.getAllAdmins();
        return ResponseEntity.ok(allAdmins);
    }
}