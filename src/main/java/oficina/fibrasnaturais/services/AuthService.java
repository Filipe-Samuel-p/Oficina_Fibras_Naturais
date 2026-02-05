package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.loginDTO.LoginRequestDTO;
import oficina.fibrasnaturais.DTOs.loginDTO.LoginResponseDTO;
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

    public AuthService(UserRepository repository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
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
}