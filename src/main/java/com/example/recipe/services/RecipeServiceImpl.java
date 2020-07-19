package com.example.recipe.services;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.converters.RecipeCommandToRecipe;
import com.example.recipe.converters.RecipeToRecipeCommand;
import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Slf4j   //slf4j is a debug logger, through log() amongst other features
@Service  //bridges CRUD-RecipeRepository with RecipeService (declares getRecipes() and is overridden here)
public class RecipeServiceImpl implements RecipeService {

    // CRUD-style enabled class to handles Recipes and Long IDs
    private final RecipeRepository recipeRepository;
    //handle command-objects
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Now in RecipeServiceImpl.getRecipes()");
        Set<Recipe> recipeSet = new HashSet<>();

        //findAll() returns an Iterable<Recipe>, pass each recipe in the Repo to RecipeSet and return
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {

        //pull over the recipe from the CRUD repo, given the id
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found!");
        }

        return recipeOptional.get();
    }

    // only expected to run from web UI
    // converts command type to POJO and then merges the POJO data to the CRUD based repo (or creates a new repo if the
    // db is empty), recipeRepository (see constructor); finally it returns the same data back
    // the sequence (command-to-POJO-to-command) is handled outside of JPA as a group, a transaction
    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
