package oficina.fibrasnaturais.DTOs.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String password;

    private String phone;
    private AddressDTO address;

    public UserDTO(User entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        phone = entity.getPhone();
        if (entity.getAddress() != null) {
            address = new AddressDTO(entity.getAddress());
        }
    }
}
