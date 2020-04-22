package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes convertObject) {

        if (convertObject == null) {
            return null;
        }

        return new NotesCommand()
                .setId(convertObject.getId())
                .setRecipeNotes(convertObject.getRecipeNotes());

    }
}
