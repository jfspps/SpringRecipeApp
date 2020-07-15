package com.example.recipe.controllers;

import com.example.recipe.model.Recipe;
import com.example.recipe.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

    @Test
    public void getIndexPage_arguments() {
        //use the given, when, then for behaviour testing
        /**
                (Given) some context
                (When) some action is carried out
                (Then) a particular set of observable consequences should obtain
        */

        String modelString = "recipes";

        //given
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);

        // ArgumentCaptor<T> is a set which stores arguments passed between calls related to object T
        // the new instance pertains specifically to Set methods
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String returnString = indexController.getIndexPage(model);


        //then
        assertEquals("index", returnString);
        //capture the arguments passed between Model.addAttribute
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        //pull argument data across to a set and check if two
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testMockMVC() throws Exception {
        // enables routing to be verified without loading the entire project by manually checking multiple GET requests
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/")).
                andExpect(status().isOk()).
                andExpect(view().name("index"));
    }
}