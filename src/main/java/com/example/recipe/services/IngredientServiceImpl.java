package com.example.recipe.services;

import com.example.recipe.commands.IngredientCommand;
import com.example.recipe.converters.IngredientCommandToIngredient;
import com.example.recipe.converters.IngredientToIngredientCommand;
import com.example.recipe.model.Ingredient;
import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import com.example.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        //query the recipe first before the ingredient
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        //match the captured recipe with the ingredient with i ingredientId, and then convert to IngredientCommand
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        //if we can't find ingredient then this'll need handling in future
        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    //verify all commands are performed before committing to the DB
    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        // Overall strategy is build a Recipe (parent) POJO, save it to the DB (or update if it exists)
        // and then return the IngredientCommand property. Thus associated properties persist.

        // make sure that the recipe property of ingredient is initialised, then extract
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            // save() will not return null but will not save anything to the DB if recipe property is missing
            return new IngredientCommand();
        } else {
            // convert Optional<Recipe> to Recipe and extract all ingredients then pull out ingredient by ID
            Recipe recipe = recipeOptional.get();

            // this approach verifies a recipe has been associated with the IngredientCommand
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            // if an associated ingredient is found, then assign the other properties with a ingredient POJO
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();

                //build temporary Ingredient POJO from IngredientCommand
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());

                //extract and apply the UOM
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //...if not, build a new POJO from command in its present state and align with recipe POJO
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            //save Recipe and all changes to each associated ingredient to the DB
            Recipe savedRecipe = recipeRepository.save(recipe);

            //new verification code from this point (for new ingredients)======================================

            //get the new ingredient
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description if the above stream fails
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long idToDelete) {

        //recipe to ingredient is one-to-many

        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                //clear the recipe reference from ingredient and then remove ingredient from the Set<Ingredient>
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
    }
}
