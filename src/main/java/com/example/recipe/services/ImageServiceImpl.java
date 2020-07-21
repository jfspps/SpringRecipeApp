package com.example.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        //implement with persistence later...return a debug message
        log.debug("Received a file");

    }
}
