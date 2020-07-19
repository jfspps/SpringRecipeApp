package com.example.recipe.converters;

import com.example.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    //unit tests to and from command objects via converter are transferable...

    //dependencies
    final String description = "Test uomCommand";
    final Long id = 1L;
    UnitOfMeasureCommand unitOfMeasureCommand;

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convert() {
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(id);
        unitOfMeasureCommand.setDescription(description);

        UnitOfMeasure test = converter.convert(unitOfMeasureCommand);

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
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }
}