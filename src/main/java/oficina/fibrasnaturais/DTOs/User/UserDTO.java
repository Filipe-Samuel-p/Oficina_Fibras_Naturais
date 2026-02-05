package oficina.fibrasnaturais.DTOs.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oficina.fibrasnaturais.entities.User;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public UserDTO(User entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        phone = entity.getPhone();
    }
}
