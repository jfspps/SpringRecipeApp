package com.example.recipe.model;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    Category category;

    @org.junit.Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @org.junit.Test
    public void getId() {
        Long idValue = 4l;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @org.junit.Test
    public void getDescription() {
    }

    @org.junit.Test
    public void getRecipes() {
    }
}