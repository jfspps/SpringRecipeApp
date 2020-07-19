package com.example.recipe.controllers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class recipeController {

    private final RecipeService recipeService;

    public recipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // not mandatory, @GetMapping limits methods to GET request type calls, similarly @PostMapping limits calls to POST

    //pass the id (entered as part of the URL) and retrieve a Recipe object and send it to show.html
    @RequestMapping("/recipe/{id}/show")
    @GetMapping
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    //posted form accessed through recipe/new will create a blank RecipeCommand object
    @RequestMapping("recipe/new")
    @GetMapping
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

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    //called when update (from index.html) is selected
    @RequestMapping("recipe/{id}/update")
    @GetMapping
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        //part of SLF4J debugging
        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
