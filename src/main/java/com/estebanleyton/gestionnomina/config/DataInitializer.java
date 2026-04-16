package com.estebanleyton.gestionnomina.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.estebanleyton.gestionnomina.model.Usuario;
import com.estebanleyton.gestionnomina.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // ================= ADMIN =================
            if (usuarioRepository.findByUsername("admin").isEmpty()) {

                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol("ADMIN");
                admin.setEstado(true);

                usuarioRepository.save(admin);
                System.out.println("✔ Usuario ADMIN creado");
            }

            // ================= RRHH =================
            if (usuarioRepository.findByUsername("rrhh").isEmpty()) {

                Usuario rrhh = new Usuario();
                rrhh.setUsername("rrhh");
                rrhh.setPassword(encoder.encode("rrhh123"));
                rrhh.setRol("RRHH");
                rrhh.setEstado(true);

                usuarioRepository.save(rrhh);
                System.out.println("✔ Usuario RRHH creado");
            }

            // ================= SUPERVISOR =================
            if (usuarioRepository.findByUsername("supervisor").isEmpty()) {

                Usuario supervisor = new Usuario();
                supervisor.setUsername("supervisor");
                supervisor.setPassword(encoder.encode("super123"));
                supervisor.setRol("SUPERVISOR");
                supervisor.setEstado(true);

                usuarioRepository.save(supervisor);
                System.out.println("✔ Usuario SUPERVISOR creado");
            }
        };
    }
}
