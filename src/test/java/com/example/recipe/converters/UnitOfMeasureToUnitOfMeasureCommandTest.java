package com.example.recipe.converters;

import com.example.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    //unit tests to and from command objects via converter are transferable...

    //dependencies
    final String description = "Test uom";
    final Long id = 1L;
    UnitOfMeasure unitOfMeasure;

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() {
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(id);
        unitOfMeasure.setDescription(description);

        UnitOfMeasureCommand test = converter.convert(unitOfMeasure);

        //check everything passed on
        assertEquals(id, test.getId());
        assertEquals(description, test.getDescription());
    }

    // allow for acceptance of null
    // allow for return of null

    @Test
    void testNullInput() {
        assertNull(converter.convert(null));
    }

    @Test
    void testNullOutput() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
}