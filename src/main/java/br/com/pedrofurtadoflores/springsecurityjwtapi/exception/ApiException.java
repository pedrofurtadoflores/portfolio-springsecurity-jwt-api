package br.com.pedrofurtadoflores.springsecurityjwtapi.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "Error object returned by the API in case of exceptions")
public class ApiException {

    @Schema(description = "HTTP status code of the error", example = "404")
    private int status;

    @Schema(description = "Error message", example = "User not found")
    private String message;

    @Schema(description = "Timestamp when the error occurred", example = "2025-06-13T15:42:00")
    private LocalDateTime timestamp;
}
