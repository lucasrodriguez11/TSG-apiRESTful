package com.tsg.application.rest.controller;

import com.tsg.application.rest.dto.request.UserRequest;
import com.tsg.application.rest.dto.response.UserResponse;
import com.tsg.application.rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para la gestión de usuarios.
 * <p>
 * Proporciona endpoints para obtener, actualizar y eliminar usuarios.
 * </p>
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
public class UserController {

    private final UserService userService;

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return ResponseEntity con una lista de UserResponse.
     */
    @Operation(summary = "Obtener listado de usuarios", description = "Recupera una lista de todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios recuperados exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene la información de un usuario por su ID.
     *
     * @param id Identificador del usuario.
     * @return ResponseEntity con el UserResponse correspondiente.
     */
    @Operation(summary = "Obtener usuario por ID", description = "Recupera la información de un usuario específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserResponseById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param id          Identificador del usuario a actualizar.
     * @param userRequest Objeto que contiene la nueva información del usuario.
     * @return ResponseEntity con un mensaje de éxito.
     */
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario. Requiere autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateUser(id, userRequest);
        return ResponseEntity.ok("Usuario actualizado con éxito");
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id Identificador del usuario a eliminar.
     * @return ResponseEntity con un mensaje de éxito.
     */
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario registrado. Requiere autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado con éxito");
    }
}
