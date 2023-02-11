package com.example.electronicstore.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ImageService {

    public boolean uploadImage(MultipartFile image, String serviceName){
        boolean isUploaded = false;

        try{
            File imageDirectory = new File(serviceName);
            if(!imageDirectory.exists()){
                imageDirectory.mkdirs();
            }

            Files.copy(image.getInputStream(), Paths.get(serviceName + image.getOriginalFilename()));
            isUploaded = true;

        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return isUploaded;
    }
}
