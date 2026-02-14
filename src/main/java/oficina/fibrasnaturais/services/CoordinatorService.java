package oficina.fibrasnaturais.services;

import jakarta.transaction.Transactional;
import oficina.fibrasnaturais.exceptions.ConflictException;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoordinatorService {

    private final UserRepository userRepository;

    public CoordinatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteAdmin(UUID adminId, UUID currentCoordinatorId) {

        var targetUser = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado."));


        if (targetUser.getId().equals(currentCoordinatorId)) {
            throw new ConflictException("Você não pode deletar a si mesma.");
        }

        var isTargetCoordinator = targetUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_COORDINATOR"));

        if (isTargetCoordinator) {
            throw new ConflictException("Uma coordenadora não pode deletar outra coordenadora.");
        }

        userRepository.delete(targetUser);
    }
}