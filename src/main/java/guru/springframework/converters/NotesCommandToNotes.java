package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        return new Notes()
                .setId(convertObject.getId())
                .setRecipeNotes(convertObject.getRecipeNotes());
    }
}
