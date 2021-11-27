package com.cursos.summerschool.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.cursos.summerschool.config.BucketName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageStore {

    private final AmazonS3 amazonS3;


    public boolean upload(MultipartFile file) {

        if(file.isEmpty()) {
            throw new IllegalStateException("No se puede subir un archivo vacío");
        }

        //TODO revisar los tipos de imágenes permitidas

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-type", file.getContentType());
        metadata.put("Content-length", String.valueOf(file.getSize()));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        metadata.forEach(objectMetadata::addUserMetadata);

        String path = String.format("%s%s",BucketName.SUMMERSCHOOL_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s",file.getOriginalFilename());

        try {
            amazonS3.putObject(path, fileName, file.getInputStream(), objectMetadata);
        }
        catch (IOException e) {
            throw new IllegalStateException("Falló la subida de tu foto a la página.", e);
        }
        return true;
    }
}
