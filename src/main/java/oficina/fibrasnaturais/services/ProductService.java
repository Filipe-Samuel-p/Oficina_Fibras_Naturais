package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public Page<ProductDTO> getAllProducts (Pageable pageable){
        var allProducts = repository.findAll(pageable);
        return allProducts.map(ProductDTO::new);
    }


    public ProductDTO getProduct (Long productID) {

        var product = repository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Producto não encontrado"));

        return new ProductDTO(product);


    }
}
