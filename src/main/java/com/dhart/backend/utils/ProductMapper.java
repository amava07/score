package com.dhart.backend.utils;

import com.dhart.backend.model.Category;
import com.dhart.backend.model.Feature;
import com.dhart.backend.model.Product;
import com.dhart.backend.model.dto.FeatureDTO;
import com.dhart.backend.model.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product productDtoToProduct(Long id, ProductDTO productDTO){
        Category category = Category.builder()
                .id(productDTO.getIdCategory())
                .build();

        Product product = Product.builder()
                .id(id)
                .title(productDTO.getTitle())
                .description(productDTO.getDescription())
                .author(productDTO.getDescription())
                .location(productDTO.getLocation())
                .technique(productDTO.getTechnique())
                .year(productDTO.getYear())
                .priceHour(productDTO.getPriceHour())
                .available(productDTO.getAvailable())
                .imagePath(productDTO.getImagePath())
                .imageUrl(productDTO.getImageUrl())
                .urlList(productDTO.getUrlList())
                .category(category)
                .build();

        return product;
    }

    public static ProductDTO productToProductDto(Product product){
        List<FeatureDTO> featureDTOS = new ArrayList<>();
        for (Feature feature: product.getFeatures()) {
            FeatureDTO featureDTO = FeatureDTO.builder()
                    .id(feature.getId())
                    .icons(feature.getIcons())
                    .description(feature.getDescription())
                    .build();

            featureDTOS.add(featureDTO);
        }


        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .author(product.getAuthor())
                .location(product.getLocation())
                .technique(product.getTechnique())
                .year(product.getYear())
                .priceHour(product.getPriceHour())
                .available(product.getAvailable())
                .imagePath(product.getImagePath())
                .imageUrl(product.getImageUrl())
                .urlList(product.getUrlList())
                .idCategory(product.getCategory().getId())
                .features(featureDTOS)
                .build();

        return productDTO;

    }

}
