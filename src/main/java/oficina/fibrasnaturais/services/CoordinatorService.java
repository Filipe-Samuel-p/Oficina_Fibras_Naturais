package oficina.fibrasnaturais.services;

import jakarta.transaction.Transactional;
import oficina.fibrasnaturais.enums.RoleName;
import oficina.fibrasnaturais.exceptions.ConflictException;
import oficina.fibrasnaturais.exceptions.ResourceNotFoundException;
import oficina.fibrasnaturais.repositories.RoleRepository;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoordinatorService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CoordinatorService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        try{
            userRepository.delete(targetUser);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Falha de integridade referencial");
        }

    }


    public void updateAdminStatus (UUID adminId){

        var targetUser = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado."));

        var isTargetAdmin = targetUser.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isTargetAdmin) {
            throw new ConflictException("O usuário não é um administrador");
        }

        var roleCoordenatior = roleRepository.findByAuthority(RoleName.ROLE_COORDINATOR);

        targetUser.getRoles().add(roleCoordenatior);

        targetUser = userRepository.save(targetUser);

    }
}