package com.dhart.backend.controller;

import com.dhart.backend.model.vm.Asset;
import com.dhart.backend.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    S3Service s3Service;

    @PostMapping("/upload")
    Map<String, String> upload (@RequestPart(value = "file") @RequestParam MultipartFile file){
        String key = s3Service.putObject(file);

        Map<String, String> result = new HashMap<>();
        result.put("key",key);
        result.put("url", s3Service.getObjectUrl(key));

        return result;
    }

    @PostMapping("/uploadFiles")
    List<Map<String, String>> uploadFiles (@RequestPart(value ="files") @RequestParam List<MultipartFile>files){

        List<Map<String,String>> result = new ArrayList<>();

        for(MultipartFile file: files){

            Map<String, String> re = new HashMap<>();
            String key = s3Service.putObject(file);
            re.put("key", key);
            re.put("url", s3Service.getObjectUrl(key));

            result.add(re);
        }
        return result;
    }


    @GetMapping(value = "/get-object", params = "key")
    ResponseEntity <ByteArrayResource> getObject(@RequestParam String key) {
        Asset asset = s3Service.getObject(key);
        ByteArrayResource resource = new ByteArrayResource(asset.getContent());

        return ResponseEntity
                .ok()
                .header("Content-Type", asset.getContentType())
                .contentLength(asset.getContent().length)
                .body(resource);
    }


    @DeleteMapping(value = "delete-object", params = "key")
    void deleteObject (@RequestParam String key){
        s3Service.deleteObject(key);
    }
}