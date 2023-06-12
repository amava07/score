package com.dhart.backend.controller;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.Product;
import com.dhart.backend.model.Score;
import com.dhart.backend.model.dto.ScoreDTO;
import com.dhart.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<String> saveScore(@RequestBody ScoreDTO score) throws NotFoundException {
        scoreService.saveScore(score);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully registered score");
    }

    @GetMapping("/average/{id}")
    public ResponseEntity<Double> getAvgByProductId(@PathVariable Long id) throws NotFoundException {
        Optional<Double> avgByProductId = scoreService.getAvgByProductId(id);
        if(avgByProductId.isPresent()) {
            return ResponseEntity.ok(avgByProductId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{idProduct}/user/{idUser}")
    public ResponseEntity<ScoreDTO> findScore(@PathVariable Long idProduct, @PathVariable Long idUser) throws NotFoundException {
       Optional<ScoreDTO> score = scoreService.findScoreByProductAndUser(idProduct, idUser);
       return ResponseEntity.ok(score.get());
    }

    @DeleteMapping ("/scores/{idProduct}/delete")
    public  ResponseEntity<String> deleteScore(@PathVariable Long idProduct ) throws NotFoundException { //String?
        scoreService.deleteScoreByProductId(idProduct);
        return ResponseEntity.status(HttpStatus.OK).body("Product Score Delete Successfully");
    }

    @GetMapping ("scores/{idScore}/")
    public Optional<ScoreDTO> findScoreByIdScore ( @PathVariable Long idScore ) throws NotFoundException {
        return scoreService.findScoreByIdScore(idScore);
    }

}
