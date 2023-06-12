package com.dhart.backend.repository;

import com.dhart.backend.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    //Método para poder buscar un usuario mediante su nombre
    Optional<Usuarios> findByEmail(String email);

    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByEmail(String email);
    List<Usuarios> findAll();

}
