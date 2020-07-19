package com.example.recipe.converters;

import com.example.recipe.commands.NotesCommand;
import com.example.recipe.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

//see UnitOfMeasure related classes in /converters for background info
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source != null){
            final Notes notes = new Notes();
            notes.setId(source.getId());
            notes.setRecipe(source.getRecipe());
            notes.setRecipeNotes(source.getRecipeNotes());
            return notes;
        } else
            return null;
    }
}
