package com.estebanleyton.gestionnomina.service;

import com.estebanleyton.gestionnomina.dto.LoginDTO;
import com.estebanleyton.gestionnomina.model.Usuario;

public interface UsuarioService {

    Usuario login(LoginDTO dto);
}
