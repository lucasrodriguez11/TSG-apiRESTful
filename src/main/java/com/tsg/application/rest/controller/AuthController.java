package com.tsg.application.rest.controller;

import com.tsg.application.rest.dto.request.LoginRequest;
import com.tsg.application.rest.dto.request.RegisterRequest;
import com.tsg.application.rest.dto.response.LoginResponse;
import com.tsg.application.rest.dto.response.RegisterResponse;
import com.tsg.application.rest.model.User;
import com.tsg.application.rest.security.JwtUtil;
import com.tsg.application.rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para la autenticación y registro de usuarios.
 * <p>
 * Proporciona endpoints para el login y registro de nuevos usuarios.
 * </p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param loginRequest Objeto con las credenciales de inicio de sesión.
     * @return ResponseEntity con un objeto LoginResponse que incluye el token JWT y los detalles del usuario.
     */
    @Operation(summary = "Autenticar usuario", description = "Valida las credenciales y genera un token JWT para el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .status("success")
                .message("Inicio de sesión exitoso.")
                .token(jwt)
                .username(userDetails.getUsername())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registerRequest Objeto que contiene los datos del nuevo usuario.
     * @return ResponseEntity con un objeto RegisterResponse confirmando el registro.
     */
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User newUser = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        userService.createUser(newUser);

        RegisterResponse response = RegisterResponse.builder()
                .status("success")
                .message("El usuario ha sido registrado exitosamente.")
                .username(newUser.getUsername())
                .build();

        return ResponseEntity.ok(response);
    }
}
