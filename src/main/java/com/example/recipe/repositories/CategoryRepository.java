package com.example.recipe.repositories;

import com.example.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // New to Spring 5, Optional (Java 8+) provides a way to convey the message that there may not be a
    // value, without using null. This reduces the number of NullPointerExceptions.s

    //call query method and return a Category object (Category can be null) based on description
    Optional<Category> findByDescription(String description);
}
