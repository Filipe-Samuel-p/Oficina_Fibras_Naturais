package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.User.AddressDTO;
import oficina.fibrasnaturais.DTOs.User.UserDTO;
import oficina.fibrasnaturais.entities.Address;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.AddressRepository;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;
    private AddressRepository addressRepository;


    public UserService(UserRepository repository, AddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
    }

    public UserDTO getUserProfile (JwtAuthenticationToken token){

        var userID = UUID.fromString(token.getName());
        var user = repository.findById(userID).
                orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));

        return new UserDTO(user);
    }

    public AddressDTO addAddress (AddressDTO dto, JwtAuthenticationToken token){

        var userID = UUID.fromString(token.getName());
        var user = repository.findById(userID).
                orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));

        var address = new Address();
        dtoAddressToEntity(dto,address);
        address.setUser(user);
        var finalAddress = addressRepository.save(address);

        return new AddressDTO(finalAddress);

    }

    private void dtoAddressToEntity(AddressDTO dto, Address entity){
        entity.setCity(dto.getCity());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setStreet(dto.getStreet());
        entity.setReference(dto.getReference());
        entity.setZipCode(dto.getZipCode());
        entity.setNumber(dto.getNumber());
    }

    public AddressDTO updateAddress(AddressDTO dto, JwtAuthenticationToken token) {

        var userID = UUID.fromString(token.getName());

        var user = repository.findById(userID)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));

        var addressID = user.getAddress().getId();

        var address = addressRepository.findById(addressID)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));


        if (!address.getUser().getId().equals(userID)) {
            throw new BadCredentialsException("Você não está autorizado a atualizar este endereço");
        }

        if (dto.getStreet() != null) {
            address.setStreet(dto.getStreet());
        }

        if (dto.getZipCode() != null) {
            address.setZipCode(dto.getZipCode());
        }

        if (dto.getCity() != null) {
            address.setCity(dto.getCity());
        }

        if (dto.getNeighborhood() != null) {
            address.setNeighborhood(dto.getNeighborhood());
        }

        if (dto.getReference() != null) {
            address.setReference(dto.getReference());
        }

        if (dto.getNumber() != null) {
            address.setNumber(dto.getNumber());
        }

        var updatedAddress = addressRepository.save(address);

        return new AddressDTO(updatedAddress);
    }

}
