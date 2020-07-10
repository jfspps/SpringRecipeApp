package com.example.recipe.model;

import javax.persistence.*;

@Entity
public class UnitOfMeasure {
    //unidirectional relationship from Ingredient so not @OneToOne annotation here

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String unitOfMeasure) {
        this.description = unitOfMeasure;
    }
}
