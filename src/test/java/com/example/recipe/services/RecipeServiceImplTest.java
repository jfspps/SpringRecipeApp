package com.example.recipe.services;

import com.example.recipe.converters.RecipeCommandToRecipe;
import com.example.recipe.converters.RecipeToRecipeCommand;
import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    //create a test implementation of RecipeRepository (a mock), needed for Impl constructor
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp(){
        // initiate a mock based on RecipeServiceImpl
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);    }

    @Test
    public void getRecipes_empty() {

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 0);    //Mockito creates a instance of Impl and recipes returns an empty set
    }

    @Test
    public void getRecipes_nonEmpty() {

        //the following mockito tests are run independently of the Spring framework and its dependency injection

        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        // mockito statement for objects labelled @Mock: a call to recipeRepository.findAll() (part of getRecipes())
        // returns the hashSet above
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);    //Mockito creates a instance of Impl and recipes returns an empty set

        //Another @Mock; verify that recipeRepository.findAll() was called once only, for above mockito test
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //no 'when()', since deleteById has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}