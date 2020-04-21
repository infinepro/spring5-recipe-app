package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<NotesCommand, Notes> {

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public NotesToNotesCommand(RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes convertObject) {

        if (convertObject == null) {
            return null;
        }

        RecipeCommand recipeCommand = recipeToRecipeCommand
                .convert(convertObject.getRecipe());

        return new NotesCommand()
                .setId(convertObject.getId())
                .setRecipeNotes(convertObject.getRecipeNotes())
                .setRecipe(recipeCommand);

    }
}
