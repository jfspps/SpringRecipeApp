package com.example.recipe.services;

import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws Exception{
        //given
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "This text will be stored in testing.txt".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // record all arguments passed to Recipe methods
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile);

        //then (verify that save() was called once and the MultipartFile and Byte[] match
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}