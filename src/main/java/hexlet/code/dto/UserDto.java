package hexlet.code.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "First Name should not be empty")
    private String firstName;

    @NotBlank(message = "Last Name should not be empty")
    private String lastName;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Incorrect Email")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 3, max = 100, message = "Password should have from 3 to 100 chars")
    private String password;

    public void setHashedPwd(String password) {
        this.password = password;
    }
}
