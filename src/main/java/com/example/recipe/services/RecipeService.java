package com.example.recipe.services;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.model.Recipe;

import java.util.Set;

//Spring 5 @Service is handled by RecipeServiceImpl
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    //needed to handle RecipeCommand objects prior to conversion to POJO (ultimately called on POST requests)
    RecipeCommand saveRecipeCommand(RecipeCommand command);

    //handle command objects directly (will convert to/from POJO)
    RecipeCommand findCommandById(Long l);

    void deleteById(Long idToDelete);
}
