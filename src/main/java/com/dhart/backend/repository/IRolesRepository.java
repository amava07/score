package com.dhart.backend.repository;

import com.dhart.backend.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {
    //Método para buscar un role por su nombre en nuestra base de datos
    Optional<Roles> findByName(String name);

    // Método para verificar la existencia de un role por su nombre
    boolean existsByName(String roleName);
}

