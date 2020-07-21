package com.example.recipe.controllers;

import com.example.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//slf4j is a debug logger, through log() amongst other features
@Slf4j
@Controller
public class indexController {

    private final RecipeService recipeService;

    public indexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // not mandatory, @GetMapping limits methods to GET request type calls, similarly @PostMapping limits calls to POST

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Now in indexController.getIndexPage");
        //retrieve Set<Recipe> with getRecipes() and send to index.html, identified as recipes to thymeleaf
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
