package oficina.fibrasnaturais.services;

import oficina.fibrasnaturais.DTOs.shopConfig_admin.AdminDTO;
import oficina.fibrasnaturais.DTOs.shopConfig_admin.ShopConfigDTO;
import oficina.fibrasnaturais.entities.Role;
import oficina.fibrasnaturais.entities.ShopConfig;
import oficina.fibrasnaturais.entities.User;
import oficina.fibrasnaturais.enums.RoleName;
import oficina.fibrasnaturais.exceptions.ConflictException;
import oficina.fibrasnaturais.repositories.RoleRepository;
import oficina.fibrasnaturais.repositories.ShopConfigRepository;
import oficina.fibrasnaturais.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopConfigService {

    private final ShopConfigRepository configRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ShopConfigService(ShopConfigRepository configRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.configRepository = configRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(readOnly = true)
    public ShopConfigDTO getMainConfig() {
        ShopConfig config = configRepository.findMainConfig()
                .orElse(new ShopConfig());

        var dto = new ShopConfigDTO();
        dto.setWhatsappNumber(config.getWhatsappNumber() != null ? config.getWhatsappNumber() : "5522");
        return dto;
    }

    @Transactional
    public ShopConfigDTO updateConfig(ShopConfigDTO dto) {
        var config = configRepository.findMainConfig()
                .orElse(new ShopConfig());

        config.setWhatsappNumber(dto.getWhatsappNumber());

        config = configRepository.save(config);

        dto.setWhatsappNumber(config.getWhatsappNumber());
        return dto;
    }


    @Transactional
    public AdminDTO registerNewAdmin(AdminDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Já existe um usuário com este e-mail.");
        }

        var adminRole = roleRepository.findByAuthority(RoleName.ROLE_ADMIN);

        User newAdmin = new User();
        newAdmin.setName(dto.getName());
        newAdmin.setEmail(dto.getEmail());
        newAdmin.setPhone(dto.getPhoneAdmin());
        newAdmin.setPassword(passwordEncoder.encode(dto.getPassword()));

        newAdmin.setRoles(Set.of(adminRole));

        userRepository.save(newAdmin);

        return new AdminDTO(newAdmin);
    }

    public List<AdminDTO> getAllAdmins () {

        var allUsers = userRepository.findAll();
        var allAdmins = new ArrayList<User>();
        var roleAdmin = roleRepository.findByAuthority(RoleName.ROLE_ADMIN);
        for (User user : allUsers){
            if (user.getRoles().contains(roleAdmin)) {
                allAdmins.add(user);
            }
        }

        return allAdmins.stream().map(AdminDTO::new).toList();
    }
}