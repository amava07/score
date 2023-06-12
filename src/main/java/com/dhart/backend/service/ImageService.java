package com.dhart.backend.service;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.Category;
import com.dhart.backend.model.Image;
import com.dhart.backend.model.Product;
import com.dhart.backend.model.dto.CategoryDTO;
import com.dhart.backend.model.dto.ImageDTO;
import com.dhart.backend.model.dto.ProductDTO;
import com.dhart.backend.repository.ImageRepository;
import com.dhart.backend.utils.CategoryMapper;
import com.dhart.backend.utils.ImageMapper;
import com.dhart.backend.utils.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
   private final ImageRepository imageRepository;
   private ObjectMapper mapper;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDTO saveImage(ImageDTO imageDTO) throws RegisteredResourceException {
        Image image = ImageMapper.imageDtoToImage(null, imageDTO);

        imageRepository.save(image);

        return imageDTO;
    }


    public List<ImageDTO> findAllImages(){

        List<Image> images = imageRepository.findAll();
        List<ImageDTO> imageDTOList = new ArrayList<ImageDTO>();
        for (Image image: images) {
            ImageDTO imageDTO = ImageMapper.imageToImageDto(image);
            imageDTOList.add(imageDTO);
        }
        return imageDTOList;
    }

    public Optional<ImageDTO> findImageById(Long id) {
        Optional<ImageDTO> imageDTOOptional = null;
        Optional<Image> image = imageRepository.findById(id);

            ImageDTO imageDTO = ImageMapper.imageToImageDto(image.get());
            imageDTOOptional = Optional.of(imageDTO);

        return imageDTOOptional;
    }

    public List<ImageDTO> findAllByProduct(Long id) {
        List<Image> images = imageRepository.findAllByProduct(id);
        List<ImageDTO> imageDTOS = new ArrayList<>();

        for (Image image: images) {
            imageDTOS.add(ImageMapper.imageToImageDto(image));
        }
        return imageDTOS;
    }

    public Image findImageByUrl(String url){
        Image image = imageRepository.findByUrl(url);
        return image;

    }

    public void deleteImage(Long id) throws NotFoundException {
            findImageById(id);
            imageRepository.deleteById(id);
        }


}
