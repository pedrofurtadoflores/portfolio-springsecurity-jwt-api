package br.com.pedrofurtadoflores.springsecurityjwtapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Output DTO for returning user data")
public class UserResponseDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User's full name", example = "Maria Silva")
    private String name;

    @Schema(description = "User's email address", example = "maria@email.com")
    private String email;

    @Schema(description = "User's gender", example = "Female")
    private String gender;

    @Schema(description = "User's date of birth", example = "1990-05-21")
    private LocalDate birthDate;

    @Schema(description = "URL of the user's avatar", example = "https://mysite.com/avatar.jpg")
    private String avatarUrl;

    @Schema(description = "User status (active or inactive)", example = "true")
    private Boolean active;

    @Schema(description = "User creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "User last update timestamp")
    private LocalDateTime updatedAt;
}
