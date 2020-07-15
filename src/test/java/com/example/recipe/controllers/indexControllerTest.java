package com.example.recipe.controllers;

import com.example.recipe.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class indexControllerTest {

    // not mock testing indexController
    indexController indexController;

    //setup the required dependencies
    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    @Before
    public void setUp(){
        // initiate a mock based on RecipeServiceImpl
        MockitoAnnotations.initMocks(this);

        indexController = new indexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        String returnString = indexController.getIndexPage(model);
        String modelString = "recipes";

        //check the return string
        assertEquals("index", returnString);

        //run mock tests on Mocks

        //test recipeService.getRecipes() is called only once
        verify(recipeService, times(1)).getRecipes();

        //test if addAttribute() was called only once
        verify(model, times(1)).addAttribute(modelString, recipeService.getRecipes());

        //equals matcher eq() to check that modelString is "recipes"; anySet() is a generic Set for testing purposes
        verify(model, times(1)).addAttribute(eq(modelString), anySet());

    }
}