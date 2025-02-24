        package com.tsg.application.rest.dto.request;

        import jakarta.validation.constraints.NotNull;
        import lombok.Builder;
        import lombok.Data;
        import jakarta.validation.constraints.Email;
        import jakarta.validation.constraints.NotBlank;
        import jakarta.validation.constraints.Size;

        @Data
        @Builder
        public class RegisterRequest {
            @NotBlank(message = "El username es obligatorio")
            @NotNull(message = "El username no puede ser nulo")
            @Size(min = 4, max = 50, message = "El username debe tener entre 4 y 50 caracteres")
            private String username;

            @NotBlank(message = "El email es obligatorio")
            @NotNull(message = "El email no puede ser nulo")
            @Email(message = "El email debe ser válido")
            private String email;

            @NotBlank(message = "La contraseña es obligatoria")
            @NotNull(message = "La contraseña no puede ser nulo")
            @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
            private String password;
        }
