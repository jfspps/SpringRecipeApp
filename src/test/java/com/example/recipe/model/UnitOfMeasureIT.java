package com.example.recipe.model;

import com.example.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureIT {

    // testing the injection of a single dependency into the context and validating the input;
    // the advantage with this approach is the ability to run multiple checks off the same context without reloading
    // the context multiple times (run all tests from class level)
    // Also note that not all models are injected for this test to proceed

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void findByDescription_teaspoon() {
        //once
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void findByDescription_ounce() {
        //once
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");

        assertEquals("Ounce", unitOfMeasureOptional.get().getDescription());
    }

    // example of Hibernate query returned, FYI
    // select unitofmeas0_.id as id1_5_, unitofmeas0_.description as descript2_5_ from unit_of_measure unitofmeas0_ where unitofmeas0_.description=?
}