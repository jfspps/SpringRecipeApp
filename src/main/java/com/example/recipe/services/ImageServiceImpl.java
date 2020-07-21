package com.example.recipe.services;

import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl( RecipeRepository recipeService) {

        this.recipeRepository = recipeService;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        // overall approach here is to convert the MultipartFile into a bytestream, an array Byte[], and for each
        // element in Byte[], assign it to Recipe's image property

        // Byte is a java.lang wrapper class of byte primitives; MultipartFile is a Spring class
        // Hibernate recommendation is to wrap bytes into Byte prior to DB persistence
        log.debug("Received a file");

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            //transfer MultipartFile to Byte[]
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
