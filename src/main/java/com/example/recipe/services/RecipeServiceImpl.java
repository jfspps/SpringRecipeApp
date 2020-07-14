package com.example.recipe.services;

import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Slf4j   //slf4j is a debug logger, through log() amongst other features
@Service  //bridges CRUD-RecipeRepository with RecipeService (declares getRecipes() and is overridden here)
public class RecipeServiceImpl implements RecipeService {

    // CRUD-style enabled class to handles Recipes and Long IDs
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Now in RecipeServiceImpl.getRecipes()");
        Set<Recipe> recipeSet = new HashSet<>();

        //findAll() returns an Iterable<Recipe>, pass each recipe in the Repo to RecipeSet and return
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }
}
