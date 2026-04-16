package com.estebanleyton.gestionnomina.util;

import com.estebanleyton.gestionnomina.exception.AccessDeniedException;
import com.estebanleyton.gestionnomina.exception.BadRequestException;

public class RolValidator {

    public static void validarRol(String rolUsuario, String... rolesPermitidos) {

        if (rolUsuario == null || rolUsuario.isBlank()) {
            throw new BadRequestException("Rol no proporcionado");
        }

        for (String rol : rolesPermitidos) {
            if (rolUsuario.equalsIgnoreCase(rol)) {
                return;
            }
        }

        throw new AccessDeniedException("Acceso denegado: permisos insuficientes");
    }
}
