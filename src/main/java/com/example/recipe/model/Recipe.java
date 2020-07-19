package com.example.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data is a Lombok annotation which includes all-args-constructor, getters, setters, equals() and hashcode()
@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;

    //source is the name of the company/individual
    private String source;
    private String url;

    //recipe directions (allow for > 255 chars)
    @Lob
    private String directions;

    //image is JPA mappings related
    @Lob
    private Byte[] image;

    //don't want ORDINAL (default) which would return number; String returned is always HARD if other enums are added later
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    //directs Notes objects to be deleted if a Recipe is deleted (intro notes, not directions)
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    //an intermediate table is prepared by JPA to handle many-to-many relationships. Since we want Recipe-Category to be
    //bidirectional with one unified JOIN table we add @JoinTable called recipe_category, instead of two separate JOIN tables
    //see /h2-console with the current jdbc URL listed in Spring Boot to see this in action
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    //one recipe mapped to many possible ingredient sets (boiled eggs, fried eggs etc.); recipe is in Ingredient
    // avoid handling null ingredients by initialising to HashSet
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    //associates an Ingredient with Recipe (many-to-many) before vice versa; returns said recipe
    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
