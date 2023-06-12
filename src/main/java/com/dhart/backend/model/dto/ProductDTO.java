package com.dhart.backend.model.dto;
import com.dhart.backend.model.Feature;
import lombok.*;

import java.util.List;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String author;
    private String technique;
    private Integer year;
    private Double priceHour;
    private Boolean available;
    private String imagePath;
    private String imageUrl;
    private Long idCategory;
    private List<FeatureDTO> features;
    private List<String> urlList;

}
