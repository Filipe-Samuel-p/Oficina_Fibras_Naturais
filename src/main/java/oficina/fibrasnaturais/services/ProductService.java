package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.DTOs.user.AddressDTO;
import oficina.fibrasnaturais.entities.Address;
import oficina.fibrasnaturais.entities.Product;
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

    public ProductDTO createProduct (ProductDTO dto){

        var newProduct = new Product();
        dtoProductToEntity(dto,newProduct);
        newProduct = repository.save(newProduct);
        return new ProductDTO(newProduct);

    }

    public ProductDTO updateProduct (ProductDTO dto,Long productID){

        var product = repository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Producto não encontrado"));


        if (dto.getName() != null) {
            product.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }

        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }

        if (dto.getStockQuantity() != null) {
            product.setStockQuantity(dto.getStockQuantity());
        }

        if (dto.getImageUrl() != null) {
            product.setImageUrl(dto.getImageUrl());
        }

        if (dto.getActive() != null) {
            product.setActive(dto.getActive());
        }

        var updatedProduct = repository.save(product);

        return new ProductDTO(updatedProduct);


    }

    private void dtoProductToEntity(ProductDTO dto, Product entity){
       entity.setName(dto.getName());
       entity.setDescription(dto.getDescription());
       entity.setPrice(dto.getPrice());
       entity.setImageUrl(dto.getImageUrl());
       entity.setStockQuantity(dto.getStockQuantity());
       entity.setActive(dto.getActive());
    }
}
