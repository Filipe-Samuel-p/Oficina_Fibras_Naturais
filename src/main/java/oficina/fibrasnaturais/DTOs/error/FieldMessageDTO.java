package oficina.fibrasnaturais.DTOs.error;

import lombok.Getter;

@Getter
public class FieldMessageDTO {

    private String fieldName;
    private String message;

    public FieldMessageDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}