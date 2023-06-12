package com.dhart.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScoreDTO {
    private Long Id;
    private int score;
    private Long productId;
    private Long userId;
}
