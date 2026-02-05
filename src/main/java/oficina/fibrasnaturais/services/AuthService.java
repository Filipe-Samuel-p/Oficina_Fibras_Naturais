package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.User.UserDTO;
import oficina.fibrasnaturais.DTOs.loginDTO.LoginRequestDTO;
import oficina.fibrasnaturais.DTOs.loginDTO.LoginResponseDTO;
import oficina.fibrasnaturais.entities.User;
import oficina.fibrasnaturais.enums.RoleName;
import oficina.fibrasnaturais.repositories.RoleRepository;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthService(UserRepository repository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto){

        var user = repository.findByEmail(dto.email());

        if(user.isEmpty() || !user.get().isLoginCorrect(dto,passwordEncoder)){
            throw new BadCredentialsException("Email ou senha incorreta!");
        }

        var now = Instant.now();
        var expiresIn = 600L;
        var roles = user.get().getRoles()
                .stream()
                .map(role -> role.getAuthority().toString())
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("talkHub")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("roles", roles)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue,expiresIn);

    }

    public UserDTO newClient (UserDTO dto){

        var newEntity = new User();
        dtoRegisterToEntity(dto,newEntity);
        newEntity.getRoles().add(roleRepository.findByAuthority(RoleName.ROLE_CLIENT));
        var entity = userRepository.save(newEntity);

        return new UserDTO(entity);

    }

    private void dtoRegisterToEntity(UserDTO dto, User entity){
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPhone(dto.getPhone());
    }
}