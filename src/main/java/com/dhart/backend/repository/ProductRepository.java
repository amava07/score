package com.dhart.backend.repository;
import com.dhart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String title);

    @Modifying
    @Query(value= "select * from products order by rand()", nativeQuery = true)
    List<Product> findAllRandom();

    @Query(value= "SELECT p FROM Product p WHERE p.category.id = :id")
    List<Product> FindAllByCategory(@Param("id") Long id);

    @Query(value= "SELECT p FROM Product p WHERE lower(p.title) like %:text% or lower(p.author) like %:text%")
    List<Product> findByTitleOrAuthor(@Param("text") String text);

}


