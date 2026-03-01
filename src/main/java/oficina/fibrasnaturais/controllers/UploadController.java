package oficina.fibrasnaturais.controllers;

import oficina.fibrasnaturais.services.ImageUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
@PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
public class UploadController {

    private final ImageUploadService uploadService;

    public UploadController(ImageUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {

        // Chama o service para subir pro GCP
        String imageUrl = uploadService.uploadImage(file);

        // Devolve um JSON simples com a URL gerada: { "imageUrl": "https://..." }
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
