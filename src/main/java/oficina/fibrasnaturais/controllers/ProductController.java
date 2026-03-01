package oficina.fibrasnaturais.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Gestão de Produtos", description = "Operações relacionadas à gestão de produtos da loja")
@RequestMapping(value = "api/v1/product")
@RestController
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Obter todos os produtos", description = "Recupera uma lista paginada de todos os produtos disponíveis")
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProduct (Pageable pageable){
        var allProducts = service.getAllProducts(pageable);
        return ResponseEntity.ok(allProducts);
    }

    @Operation(summary = "Obter produto por ID", description = "Recupera um produto específico através do seu ID único")
    @GetMapping(value = "/{productID}")
    public ResponseEntity<ProductDTO> getProduct (@PathVariable Long productID){
        var product = service.getProduct(productID);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema (requer perfil ADMIN ou COODENADOR)")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<ProductDTO> createProduct (@RequestBody @Valid ProductDTO dto){
        dto = service.createProduct(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(summary = "Atualizar produto existente", description = "Atualiza os dados de um produto existente pelo seu ID (requer perfil ADMIN ou COODENADOR)")
    @PutMapping(value = "/{productID}")
    @PreAuthorize("hasAnyRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<ProductDTO> updateProduct (@RequestBody @Valid ProductDTO dto, @PathVariable Long productID){

        dto = service.updateProduct(dto,productID);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema pelo seu ID (requer perfil ADMIN ou COODENADOR)")
    @DeleteMapping(value = "/{productID}")
    @PreAuthorize("hasAnyRole('ADMIN') or hasRole('COODINATOR')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productID){
        service.deleteProduct(productID);
        return ResponseEntity.noContent().build();
    }
}

