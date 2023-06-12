package com.dhart.backend.controller;

import com.dhart.backend.model.dto.DtoAuthRespuesta;
import com.dhart.backend.model.dto.DtoLogin;
import com.dhart.backend.model.dto.DtoRegistro;
import com.dhart.backend.model.dto.RoleDTO;
import com.dhart.backend.security.JwtGenerador;
import com.dhart.backend.model.Roles;
import com.dhart.backend.model.Usuarios;
import com.dhart.backend.repository.IRolesRepository;
import com.dhart.backend.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = "*")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsuariosRepository usuariosRepository;
    private JwtGenerador jwtGenerador;

    @Autowired

    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuariosRepository usuariosRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }
    //Método para poder registrar usuarios con role "user"
    @PostMapping("register")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByEmail(dtoRegistro.getEmail())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setEmail(dtoRegistro.getEmail());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setFirstName(dtoRegistro.getFirstName()); // Nueva línea
        usuarios.setLastName(dtoRegistro.getLastName()); // Nueva línea
        Roles roles = rolesRepository.findByName("USER").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(usuarios);
        return new ResponseEntity<>("EL USUARIO HA SIDO REGISTROSO EXITOSAMENTE", HttpStatus.OK);
    }

    //Método para poder guardar usuarios de tipo ADMIN
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByEmail(dtoRegistro.getEmail())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setEmail(dtoRegistro.getEmail());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setFirstName(dtoRegistro.getFirstName()); // Nueva línea
        usuarios.setLastName(dtoRegistro.getLastName()); // Nueva línea
        Roles roles = rolesRepository.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(usuarios);
        return new ResponseEntity<>("El usuario administrador ha sido registrado exitosamente", HttpStatus.OK);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody DtoLogin dtoLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dtoLogin.getEmail(), dtoLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerador.generarToken(authentication);

            // Buscar al usuario en la base de datos
            String email = authentication.getName();
            Usuarios usuarios = usuariosRepository.findByEmail(email).orElse(null);

            if (usuarios != null) {
                String firstName = usuarios.getFirstName();
                String lastName = usuarios.getLastName();
                String role = usuarios.getRoles().get(0).getName();  // suponiendo que un usuario tiene solo un rol

                DtoAuthRespuesta respuesta = new DtoAuthRespuesta(token, firstName, lastName, role);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Removiendo el prefijo "Bearer " del token
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Validar el token
            Boolean tokenValido = jwtGenerador.validarToken(token);
            if (!tokenValido) {
                return new ResponseEntity<>("Token inválido", HttpStatus.UNAUTHORIZED);
            }

            // Autenticar el usuario basado en el token
            String email = jwtGenerador.obtenerUsernameDeJwt(token);
            Usuarios usuarios = usuariosRepository.findByEmail(email).orElse(null);

            if (usuarios != null) {
                String firstName = usuarios.getFirstName();
                String lastName = usuarios.getLastName();
                String role = usuarios.getRoles().get(0).getName();  // suponiendo que un usuario tiene solo un rol

                DtoAuthRespuesta respuesta = new DtoAuthRespuesta(token, firstName, lastName, role);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al validar el token", HttpStatus.UNAUTHORIZED);
        }
    }




    // Método para listar todos los usuarios
    @GetMapping("users")
    public ResponseEntity<List<Usuarios>> getAllUsers() {
        List<Usuarios> usuarios = usuariosRepository.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Método para crear un nuevo rol
    @PostMapping("roles")
    public ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();
        if (rolesRepository.existsByName(roleName)) {
            return new ResponseEntity<>("El rol ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Roles role = new Roles();
        role.setName(roleName);
        rolesRepository.save(role);
        return new ResponseEntity<>("Rol creado exitosamente", HttpStatus.OK);
    }






}