package com.dhart.backend.controller;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.dto.ProductDTO;
import com.dhart.backend.service.ProductService;
import com.dhart.backend.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    S3Service s3Service;

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/productRandom")
    public List<ProductDTO> findAllRandomProducts(){
        return productService.findAllRandomProducts();
    }

    @GetMapping("/byCategory/{idCategory}")
    public List<ProductDTO> findAllProductsByCategory(@PathVariable Long idCategory) throws NotFoundException {
        return productService.findAllProductsByCategory(idCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) throws NotFoundException {
        Optional<ProductDTO> product = productService.findProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byTitleOrAuthor/{text}")
    public List<ProductDTO> findProductByTitleOrAuthor(@PathVariable String text) {
        return productService.findProductByTitleOrAuthor(text);
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDTO productDTO) throws RegisteredResourceException, NotFoundException {
        productDTO.setImageUrl(s3Service.getObjectUrl(productDTO.getImagePath()));

        productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully registered product");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws NotFoundException {
        Optional<ProductDTO> product = productService.findProductById(id);
        if(product.isPresent()){
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product is successfully removed");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }
    }

}
