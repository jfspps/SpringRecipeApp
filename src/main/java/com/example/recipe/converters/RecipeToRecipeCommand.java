package com.example.recipe.converters;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.model.Category;
import com.example.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

//see UnitOfMeasure related classes in /converters for more background info
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    // note in this class with composition, we need to convert Notes, Category and Ingredients to Command objects before
    // passing to RecipeCommand

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source != null){
            final RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(source.getId());
            recipeCommand.setCookTime(source.getCookTime());
            recipeCommand.setPrepTime(source.getPrepTime());
            recipeCommand.setDescription(source.getDescription());
            recipeCommand.setDifficulty(source.getDifficulty());
            recipeCommand.setDirections(source.getDirections());
            recipeCommand.setServings(source.getServings());
            recipeCommand.setSource(source.getSource());
            recipeCommand.setUrl(source.getUrl());
            recipeCommand.setImage(source.getImage());

            //convert notes to notesCommand and assign to recipeCommand
            recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

            //category must be converted to categoryCommand before adding to recipeCommand
            if (source.getCategories() != null && source.getCategories().size() > 0){
                source.getCategories()
                        .forEach((Category category) -> recipeCommand.getCategories()
                                .add(categoryConverter.convert(category)));
            }

            //similarly...
            if (source.getIngredients() != null && source.getIngredients().size() > 0){
                source.getIngredients()
                        .forEach(ingredient -> recipeCommand.getIngredients()
                                .add(ingredientConverter.convert(ingredient)));
            }

            return recipeCommand;
        } else
            return null;
    }
}