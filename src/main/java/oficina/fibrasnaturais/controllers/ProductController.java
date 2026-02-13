package oficina.fibrasnaturais.controllers;


import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
