package com.dhart.backend.model.dto;


import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
        private Long id;
        private String title;
        private String description;
        private String imagePath;
        private String imageUrl;
}
