package com.example.recipe.commands;

import com.example.recipe.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// for background details, see UnitOfMeasureCommand

@NoArgsConstructor
@Setter
@Getter
public class CategoryCommand {

    private Long id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();
}
