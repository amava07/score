package com.dhart.backend.service;

import com.dhart.backend.model.Feature;
import com.dhart.backend.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FeatureService {

    private final FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> findAllFeatures(){
        return featureRepository.findAll();
    }

}
