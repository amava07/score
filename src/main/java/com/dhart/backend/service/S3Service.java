package com.dhart.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.dhart.backend.model.vm.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {
    private final static String BUCKET = "dhart-loadimages";


    private AmazonS3Client s3Client = new AmazonS3Client();

    public String putObject(MultipartFile multipartFile){
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extension);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try{
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata);
            s3Client.putObject(putObjectRequest);

            return key;

        }catch (IOException ex){

            throw new RuntimeException();
        }
    }

    public Asset getObject(String key) {
        S3Object s3Object = s3Client.getObject(BUCKET,key);
        ObjectMetadata metadata = s3Object.getObjectMetadata();
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new Asset(bytes,metadata.getContentType());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void deleteObject(String key){
        s3Client.deleteObject(BUCKET, key);
    }

    public String getObjectUrl (String key){

        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }

}
