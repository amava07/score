package com.dhart.backend.model.dto;

import com.dhart.backend.model.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@Builder
public class FeatureDTO {

    private Long id;
    private String description;
    private String icons;

}
