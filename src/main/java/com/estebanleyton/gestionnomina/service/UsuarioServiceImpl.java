package com.estebanleyton.gestionnomina.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estebanleyton.gestionnomina.dto.LoginDTO;
import com.estebanleyton.gestionnomina.model.Usuario;
import com.estebanleyton.gestionnomina.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Usuario login(LoginDTO dto) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(dto.getUsername());

        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        if (!usuario.isEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // IMPORTANTE: NO devolver la contraseña
        usuario.setPassword(null);

        return usuario;
    }
}
