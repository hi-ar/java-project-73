package hexlet.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequestDto {
    @NotBlank(message = "login should not be empty") //
    private String email;

    @NotBlank(message = "should not be empty") //
    private String password;
}
