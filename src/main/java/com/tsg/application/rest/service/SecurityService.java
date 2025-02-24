package com.tsg.application.rest.service;

import com.tsg.application.rest.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    /**
     * Verifica si el usuario autenticado es el mismo que se intenta modificar/eliminar.
     *
     * @param id El ID del usuario a verificar.
     * @return true si el ID coincide con el usuario autenticado, false en caso contrario.
     */
    public boolean isCurrentUser(Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return currentUserId.equals(id);
        }
        return false;
    }
}
