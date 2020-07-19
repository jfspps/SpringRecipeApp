package com.example.recipe.commands;

import com.example.recipe.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// for background details, see UnitOfMeasureCommand

@Setter
@Getter
@NoArgsConstructor
public class NotesCommand {

    private Long id;
    private Recipe recipe;
    private String recipeNotes;
}
