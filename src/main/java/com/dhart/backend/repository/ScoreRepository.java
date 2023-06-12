package com.dhart.backend.repository;

import com.dhart.backend.model.Score;
import com.dhart.backend.model.dto.ScoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value= "SELECT ROUND(AVG(score),1) FROM Score WHERE product.id = ?1")
    Double getAvgByProductId(Long id);

    @Query(value= "SELECT s FROM Score s WHERE product.id = ?1 AND usuario.idUsuario = ?2")
    Optional<Score> findScoreByProductIdAndUserId(Long idProduct, Long idUser);

    @Query("SELECT s FROM Score s WHERE product.id = ?1" )
    void deleteScoreByProductId ( Long idProduct);

    @Query ("SELECT s FROM Score s WHERE id.score = ?1 ")
    Optional<Score> findScoreByIdScore (Long idScore);


}
