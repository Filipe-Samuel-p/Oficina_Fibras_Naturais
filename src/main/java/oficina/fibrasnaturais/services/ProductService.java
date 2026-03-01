package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.product.ProductDTO;
import oficina.fibrasnaturais.DTOs.user.AddressDTO;
import oficina.fibrasnaturais.entities.Address;
import oficina.fibrasnaturais.entities.Product;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository repository;
    private ImageUploadService imageUploadService;

    public ProductService(ProductRepository repository, ImageUploadService imageUploadService) {
        this.repository = repository;
        this.imageUploadService = imageUploadService;
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

    public ProductDTO updateProduct(ProductDTO dto, Long productID) {

        var product = repository.findById(productID)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (dto.getName() != null) {
            product.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }

        if (dto.getPricePerUnit() != null) {
            product.setPricePerUnit(dto.getPricePerUnit());
        }

        if (dto.getStockQuantity() != null) {
            product.setStockQuantity(dto.getStockQuantity());
        }

        if (dto.getActive() != null) {
            product.setActive(dto.getActive());
        }

        if (dto.getImageUrl() != null) {
            String oldImageUrl = product.getImageUrl();
            String newImageUrl = dto.getImageUrl();

            if (oldImageUrl != null && !oldImageUrl.equals(newImageUrl)) {
                imageUploadService.deleteImage(oldImageUrl);
            }
            product.setImageUrl(newImageUrl);
        }

        var updatedProduct = repository.save(product);

        return new ProductDTO(updatedProduct);
    }


    public void deleteProduct (Long productID){

        boolean productExist = repository.existsById(productID);
        if(!productExist){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        try{
            repository.deleteById(productID);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Falha de integridade referencial");
        }

    }

    private void dtoProductToEntity(ProductDTO dto, Product entity){
       entity.setName(dto.getName());
       entity.setDescription(dto.getDescription());
       entity.setPricePerUnit(dto.getPricePerUnit());
       entity.setImageUrl(dto.getImageUrl());
       entity.setStockQuantity(dto.getStockQuantity());
       entity.setActive(dto.getActive());
    }
}
