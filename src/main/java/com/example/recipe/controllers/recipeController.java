package com.example.recipe.controllers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class recipeController {

    private final RecipeService recipeService;

    public recipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //pass the id (entered as part of the URL) and retrieve a Recipe object and send it to show.html
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    //posted form accessed through recipe/new will create a blank RecipeCommand object
    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    //see recipeform.html for /recipe/ POST request component
    //@ModelAttribute takes the model provided from newRecipe()
    //Recall that saveRecipeCommand converts command to POJO and back
    //return the id of the savedCommand and redirect to the show.html page
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }
}
