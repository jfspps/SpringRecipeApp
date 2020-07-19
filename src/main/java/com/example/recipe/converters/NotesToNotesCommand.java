package com.example.recipe.converters;

import com.example.recipe.commands.NotesCommand;
import com.example.recipe.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

//see UnitOfMeasure related classes in /converters for background info
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source != null){
            final NotesCommand notesCommand = new NotesCommand();
            notesCommand.setId(source.getId());
            notesCommand.setRecipe(source.getRecipe());
            notesCommand.setRecipeNotes(source.getRecipeNotes());

            return notesCommand;
        } else
            return null;
    }
}
