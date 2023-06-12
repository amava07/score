package com.dhart.backend.repository;

import com.dhart.backend.model.Image;
import com.dhart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    //findByProductId(?)
    @Query(value= "SELECT i FROM Image i WHERE i.product.id = :id")
    List<Image> findAllByProduct (@Param("id") Long id);

    @Query(value= "SELECT i FROM Image i WHERE i.imageUrl = :imageUrl")
    Image findByUrl (@Param("imageUrl") String imageUrl);
}
