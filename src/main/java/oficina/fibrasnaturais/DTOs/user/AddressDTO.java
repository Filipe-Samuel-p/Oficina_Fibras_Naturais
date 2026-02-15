package oficina.fibrasnaturais.DTOs.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oficina.fibrasnaturais.entities.Address;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Rua não pode ser vazia")
    private String street;

    @NotBlank(message = "CEP não pode ser vazio")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido, use o formato XXXXX-XXX")
    private String zipCode;

    @NotBlank(message = "Cidade não pode ser vazia")
    private String city;

    @NotBlank(message = "Bairro não pode ser vazio")
    private String neighborhood;

    private String reference;

    @NotBlank(message = "Número não pode ser vazio")
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





