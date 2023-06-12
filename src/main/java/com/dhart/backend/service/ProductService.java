package com.dhart.backend.service;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.Image;
import com.dhart.backend.model.Product;
import com.dhart.backend.model.dto.ImageDTO;
import com.dhart.backend.model.dto.ProductDTO;
import com.dhart.backend.repository.CategoryRepository;
import com.dhart.backend.repository.ImageRepository;
import com.dhart.backend.repository.ProductRepository;
import com.dhart.backend.utils.ImageMapper;
import com.dhart.backend.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ImageService imageService;

    @Autowired
    S3Service s3Service;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product: products) {
            ProductDTO productDTO = ProductMapper.productToProductDto(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }


    public List<ProductDTO> findAllRandomProducts(){
        List<Product> products = productRepository.findAllRandom();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: products) {
            productDTOS.add(ProductMapper.productToProductDto(product));
        }
        return productDTOS;
    }

    public List<ProductDTO>findAllProductsByCategory(Long idCategory) throws NotFoundException {
        if (categoryRepository.findById(idCategory).isEmpty()) throw new NotFoundException("This category doesn't exist");
        List<Product> products = productRepository.FindAllByCategory(idCategory);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: products) {
            productDTOS.add(ProductMapper.productToProductDto(product));
        }
        return productDTOS;
    }

    public void saveProduct(ProductDTO productDTO) throws RegisteredResourceException, NotFoundException {
        if(categoryRepository.findById(productDTO.getIdCategory()).isEmpty()) throw new NotFoundException("This category doesn't exist");
            Product product = ProductMapper.productDtoToProduct(null, productDTO);
            if(existsProductByTitle(product.getTitle())) throw new RegisteredResourceException("This product already created");
            else{productRepository.save(product);
                List<String> urls = productDTO.getUrlList();
                for(String url: urls){
                    Image image = imageService.findImageByUrl(url);
                    if(image !=  null){

                        image.setProduct(product);

                        ImageDTO imageDTO = ImageMapper.imageToImageDto(image);
                        imageService.saveImage(imageDTO);
                    }
                }
            }
    }

    public boolean existsProductByTitle(String title) {
        return productRepository.findByTitle(title).isPresent();
    }

    public Optional<ProductDTO> findProductById(Long id) throws NotFoundException {
        Optional<ProductDTO> productDTOOptional = null;
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            ProductDTO productDTO = ProductMapper.productToProductDto(product.get());
            productDTOOptional= Optional.of(productDTO);
        }
        else{
            throw new NotFoundException("This product doesn't exists");
        }
        return productDTOOptional;
    }

    public List<ProductDTO> findProductByTitleOrAuthor(String text) {
        List<Product> products = productRepository.findByTitleOrAuthor(text);
        System.out.println("products.size() = " + products.size());
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product: products) {
            productDTOS.add(ProductMapper.productToProductDto(product));
        }
        return productDTOS;
    }

    public void deleteProduct(Long id) throws NotFoundException {
        findProductById(id);
        productRepository.deleteById(id);
    }

}

