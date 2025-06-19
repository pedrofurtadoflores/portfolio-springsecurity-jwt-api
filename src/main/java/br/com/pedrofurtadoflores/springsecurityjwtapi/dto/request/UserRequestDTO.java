package br.com.pedrofurtadoflores.springsecurityjwtapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Input DTO for user creation or update")
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "User's full name", example = "Maria Silva")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Schema(description = "User's email address", example = "maria@email.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Schema(description = "User's password", example = "securePassword123")
    private String password;

    @Schema(description = "User's gender", example = "Female")
    private String gender;

    @Schema(description = "Date of birth", example = "1990-05-21")
    private LocalDate birthDate;

    @Schema(description = "URL of the user's avatar", example = "https://mysite.com/avatar.jpg")
    private String avatarUrl;
}
