package com.tsg.application.rest.controller;

import com.tsg.application.rest.dto.request.PostRequest;
import com.tsg.application.rest.dto.response.PostResponse;
import com.tsg.application.rest.service.PostService;
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
 * Controlador para la gestión de posts.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar posts.
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Endpoints para la gestión de posts")
public class PostController {

    private final PostService postService;

    /**
     * Obtiene una lista de todos los posts.
     *
     * @return ResponseEntity con una lista de PostResponse.
     */
    @Operation(summary = "Obtener listado de los posts", description = "Recupera una lista de todos los posts disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de posts obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .userId(post.getUser().getId())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    /**
     * Obtiene un post por su ID.
     *
     * @param id Identificador del post.
     * @return ResponseEntity con el PostResponse correspondiente.
     */
    @Operation(summary = "Obtener post por ID", description = "Recupera un post específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.getPostResponseById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * Crea un nuevo post.
     *
     * @param postRequest Objeto que contiene la información del post a crear.
     * @return ResponseEntity con un mensaje de éxito.
     */
    @Operation(summary = "Crear post", description = "Crea un nuevo post. Requiere autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post creado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return ResponseEntity.ok("Post creado con éxito");
    }

    /**
     * Actualiza un post existente.
     *
     * @param id          Identificador del post a actualizar.
     * @param postRequest Objeto que contiene la nueva información del post.
     * @return ResponseEntity con un mensaje de éxito.
     */
    @Operation(summary = "Actualizar post", description = "Actualiza un post existente. Requiere ser el propietario del post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post actualizado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado para actualizar este post"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updatePost(@Valid @PathVariable Long id, @RequestBody PostRequest postRequest) {
        postService.updatePost(id, postRequest);
        return ResponseEntity.ok("Post actualizado con éxito");
    }

    /**
     * Elimina un post existente.
     *
     * @param id Identificador del post a eliminar.
     * @return ResponseEntity con un mensaje de éxito.
     */
    @Operation(summary = "Eliminar post", description = "Elimina un post existente. Requiere ser el propietario del post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post eliminado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado para eliminar este post"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post eliminado con éxito");
    }
}
