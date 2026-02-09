package oficina.fibrasnaturais.DTOs.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oficina.fibrasnaturais.entities.Address;
import oficina.fibrasnaturais.entities.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String zipCode;
    private String city;
    private String neighborhood;
    private String reference;
    private String number;

    public AddressDTO(Address entity) {
        zipCode = entity.getZipCode();
        id = entity.getId();
        street = entity.getStreet();
        city = entity.getCity();
        neighborhood = entity.getNeighborhood();
        reference = entity.getReference();
        number = entity.getNumber();
    }
}





