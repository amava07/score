package com.dhart.backend.utils;

import com.dhart.backend.model.Image;
import com.dhart.backend.model.dto.ImageDTO;

public class ImageMapper {
    public static Image imageDtoToImage(Long id, ImageDTO imageDTO){
            Image image = Image.builder()
                    .id(id)
                    .imagePath(imageDTO.getImagePath())
                    .imageUrl(imageDTO.getImageUrl())
                    .build();
            return image;
        }

        public static ImageDTO imageToImageDto(Image image){

            ImageDTO imageDTO = ImageDTO.builder()
                    .id(image.getId())
                    .imagePath(image.getImagePath())
                    .imageUrl(image.getImageUrl())
                    .build();
            return imageDTO;
    }
}
