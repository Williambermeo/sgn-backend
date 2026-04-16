package com.estebanleyton.gestionnomina.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estebanleyton.gestionnomina.dto.LoginDTO;
import com.estebanleyton.gestionnomina.model.Usuario;
import com.estebanleyton.gestionnomina.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginDTO dto) {
        Usuario usuario = usuarioService.login(dto);
        return ResponseEntity.ok(usuario);
    }
}
