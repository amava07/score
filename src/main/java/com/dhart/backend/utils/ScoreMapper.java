package com.dhart.backend.utils;

import com.dhart.backend.model.Product;
import com.dhart.backend.model.Score;
import com.dhart.backend.model.Usuarios;
import com.dhart.backend.model.dto.ScoreDTO;


public class ScoreMapper {
    public static ScoreDTO scoreToScoreDto(Score score){
        ScoreDTO scoreDTO = ScoreDTO.builder()
                .Id(score.getId())
                .productId(score.getProduct().getId())
                .userId(score.getUsuario().getIdUsuario())
                .score(score.getScore())
                .build();

        return scoreDTO;
    }

    public static Score scoreDTOtoScore(Long id, Product product, Usuarios user, int scoreNum) {
        Score score = Score.builder()
                .Id(id)
                .product(product)
                .usuario(user)
                .score(scoreNum)
                .build();

        return score;
    }
}
