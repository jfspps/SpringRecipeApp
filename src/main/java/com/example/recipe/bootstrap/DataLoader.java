package com.example.recipe.bootstrap;

import com.example.recipe.model.Category;
import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.CategoryRepository;
import com.example.recipe.repositories.RecipeRepository;
import com.example.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    // Spring Boot: CommandLineRunner; more general Spring framework: ApplicationListener
    // need to override onApplicationEvent() instead of run()

    // initialise all three Repositories with DataLoader constructor
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }

    private void loadData(){
        // declare an ArrayList<Recipe> with two elements
        List<Recipe> recipes = new ArrayList<>(2);

        // setup unit of measure's needed as optionals
        // (in all Optional's cases, throw RuntimeException if Optional.isEmpty())


        // assign UnitOfMeasure objects with above optionals


        // similarly, build up optionals and Category's of American and Mexican, then add to Recipe



        // Start with the Guacamole recipe; directions is recipe directions, notes is intro notes on website

        // build ingredients with above unitsOfMeasure and Recipe

        // add Recipe to ArrayList<Recipe>

        // repeat for Grilled Spicy Chicken tacos


    }


}
