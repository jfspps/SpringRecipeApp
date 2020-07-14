package com.example.recipe.model;

import lombok.*;

import javax.persistence.*;

//@Data is a Lombok annotation which includes all-args-constructor, getters, setters, equals() and hashcode()
@Data
@Entity
public class UnitOfMeasure {
    //unidirectional relationship from Ingredient so not @OneToOne annotation here

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

}
