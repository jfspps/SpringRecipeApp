package com.example.recipe.converters;

import com.example.recipe.commands.IngredientCommand;
import com.example.recipe.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    // see RecipeToRecipeCommand and UnitOfMeasureToUnitOfMeasureCommand classes for background info
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUom(uomConverter.convert(ingredient.getUom()));
        //added ingredient editing functionality (if ingredient was instantiated after recipe then leave)
        if (ingredient.getRecipe() != null){
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }
        return ingredientCommand;
    }
}
