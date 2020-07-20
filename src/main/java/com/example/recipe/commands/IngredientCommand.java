package com.example.recipe.commands;

import com.example.recipe.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// for background details, see UnitOfMeasureCommand

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;
    private String description;
    private BigDecimal amount;
    private Recipe recipe;

    //need to accept the command-types, not the POJOs
    private UnitOfMeasureCommand uom;

    //not part of model at present, but required for web navigation
    private Long recipeId;

}
