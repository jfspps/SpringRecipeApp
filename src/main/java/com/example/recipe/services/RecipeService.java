package com.example.recipe.services;

import com.example.recipe.model.Recipe;

import java.util.Set;

//Spring 5 @Service is handled by RecipeServiceImpl
public interface RecipeService {

    Set<Recipe> getRecipes();
}
