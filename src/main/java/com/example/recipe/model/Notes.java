package com.example.recipe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

//@Data is a Lombok annotation which includes all-args-constructor, getters, setters, equals() and hashcode()
@Data
@EqualsAndHashCode(exclude = {"recipe"})  // /target/classes for compiled code-exclusion (exc. circular dependencies)
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    //directs JPA to allow recipeNotes to store > 255 chars (Lob is 'large object')
    @Lob
    private String recipeNotes;
}
