package com.dhart.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ImageDTO {

    private Long id;
    private String imagePath;
    private String imageUrl;
    private Long idProduct;
}
