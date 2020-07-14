package com.example.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data is a Lombok annotation which includes all-args-constructor, getters, setters, equals() and hashcode()
@Data
@EqualsAndHashCode(exclude = {"recipes"})   //see /target/classes for compiled code-exclusion
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    //categories is in Recipe; avoid null recipes by initialising to HashSet
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

}
