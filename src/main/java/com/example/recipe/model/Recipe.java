package com.example.recipe.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String prepTime;
    private String cookTime;
    private String servings;

    //not sure what source is used for yet...
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
    private Set<Category> categories;

    //one recipe mapped to many possible ingredient sets (boiled eggs, fried eggs etc.); recipe is in Ingredient
    // avoid handling null ingredients by initialising to HashSet
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
