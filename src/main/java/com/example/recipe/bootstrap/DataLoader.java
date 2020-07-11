package com.example.recipe.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    // need to figure out CommandLineRunner vs ApplicationListener ?!?!

    // initialise all three Repositories with DataLoader constructor

    @Override
    public void run(String... args) throws Exception {

    }

    private void loadData(){
        // declare an ArrayList<Recipe> with two elements


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
