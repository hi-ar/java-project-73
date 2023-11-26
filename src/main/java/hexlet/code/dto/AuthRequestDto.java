package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequestDto {
    private String email;
    private String password;
}
