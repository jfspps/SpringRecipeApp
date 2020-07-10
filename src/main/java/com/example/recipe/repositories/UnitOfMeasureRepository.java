package com.example.recipe.repositories;

import com.example.recipe.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    // New to Spring 5, Optional (Java 8+) provides a way to convey the message that there may not be a
    // value, without using null. This reduces the number of NullPointerExceptions.

    //call query method and return a UnitOfMeasure object (UnitOfMeasure can be null) based on description
    Optional<UnitOfMeasure> findByDescription(String description);
}
