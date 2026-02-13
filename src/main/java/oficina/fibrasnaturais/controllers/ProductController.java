package oficina.fibrasnaturais.controllers;


import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping(value = "api/v1/product")
@RestController
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProduct (Pageable pageable){
        var allProducts = service.getAllProducts(pageable);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping(value = "/{productID}")
    public ResponseEntity<ProductDTO> getProduct (@PathVariable Long productID){
        var product = service.getProduct(productID);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @PreAuthorize("hasAllRoles('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct (@RequestBody ProductDTO dto){
        dto = service.createProduct(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{productID}")
    @PreAuthorize("hasAllRoles('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct (@RequestBody ProductDTO dto, @PathVariable Long productID){

        dto = service.updateProduct(dto,productID);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{productID}")
    @PreAuthorize("hasAllRoles('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productID){
        service.deleteProduct(productID);
        return ResponseEntity.noContent().build();
    }
}

