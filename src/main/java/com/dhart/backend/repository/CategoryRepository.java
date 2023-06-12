package com.dhart.backend.repository;

import com.dhart.backend.model.Category;
import com.dhart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository <Category, Long> {

    Optional<Category> findByTitle(String title);

    @Query(value= "SELECT c FROM Category c WHERE lower(c.title) like %:text%")
    List<Category> findByName(@Param("text") String text);
}
