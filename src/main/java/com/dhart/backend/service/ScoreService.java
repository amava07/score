package com.dhart.backend.service;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.model.Product;
import com.dhart.backend.model.Score;
import com.dhart.backend.model.Usuarios;
import com.dhart.backend.model.dto.ScoreDTO;
import com.dhart.backend.repository.IUsuariosRepository;
import com.dhart.backend.repository.ProductRepository;
import com.dhart.backend.repository.ScoreRepository;
import com.dhart.backend.utils.ProductMapper;
import com.dhart.backend.utils.ScoreMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final IUsuariosRepository usuariosRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, IUsuariosRepository usuariosRepository, ProductRepository productRepository) {
        this.scoreRepository = scoreRepository;
        this.usuariosRepository = usuariosRepository;
        this.productRepository = productRepository;
    }

    public void saveScore(ScoreDTO scoreDto) throws NotFoundException {
        Map<String, Object> data = validatingProductAndUSer(scoreDto.getProductId(), scoreDto.getUserId());

        Score score = ScoreMapper.scoreDTOtoScore(null, (Product) data.get("Product"), (Usuarios) data.get("User"), scoreDto.getScore());

        scoreRepository.save(score);
    }

    public Optional<Double> getAvgByProductId(Long id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        Optional<Double> averageByProduct = null;
        if(product.isPresent()) {
            averageByProduct = Optional.of(scoreRepository.getAvgByProductId(id));
        } else {
            throw new NotFoundException("Product id not found");
        }
        return averageByProduct;
    }

    public Optional<ScoreDTO> findScoreByProductAndUser(Long idProduct, Long idUser) throws NotFoundException {
        validatingProductAndUSer(idProduct, idUser);
        Optional<Score> score = scoreRepository.findScoreByProductIdAndUserId(idProduct, idUser);

        if(!score.isPresent()) {
            throw new NotFoundException("Score not found");
        }

        ScoreDTO scoreDto = ScoreMapper.scoreToScoreDto(score.get());
        return Optional.of(scoreDto);
    }

    public Map<String, Object> validatingProductAndUSer(Long idProduct, Long idUser) throws NotFoundException {
        Map<String, Object> obj = new HashMap<>();

        Optional<Product> product = productRepository.findById(idProduct);
        Optional<Usuarios> user = usuariosRepository.findById(idUser);

        if(!product.isPresent()) {
            throw new NotFoundException("Product not found");
        }
        else if(!user.isPresent()) {
            throw new NotFoundException("User not found");
        } else {
            obj.put("Product", product.get());
            obj.put("User", user.get());
        }
        return obj;
    }

    public void deleteScoreByProductId ( Long idProduct){
        scoreRepository.deleteScoreByProductId(idProduct); //Msj que fue eliminado al front?
    }

    public Optional<ScoreDTO> findScoreByIdScore (Long idScore) throws NotFoundException{
        Optional<Score> score = scoreRepository.findScoreByIdScore(idScore);

        if(!score.isPresent()) {
            throw new NotFoundException("Score not found");
        }

        ScoreDTO scoreDto = ScoreMapper.scoreToScoreDto(score.get());
        return Optional.of(scoreDto);

    }


}
