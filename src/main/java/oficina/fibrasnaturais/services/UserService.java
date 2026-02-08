package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.User.UserDTO;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDTO getUserProfile (JwtAuthenticationToken token){

        System.out.println("NAO PEGOU O USUÁRIO");

        var userID = UUID.fromString(token.getName());
        var user = repository.findById(userID).
                orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));

        System.out.println("PEGOU O USUÁRIO");

        return new UserDTO(user);
    }
}
