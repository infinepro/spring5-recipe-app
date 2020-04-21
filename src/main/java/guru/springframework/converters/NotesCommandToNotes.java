package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<Notes, NotesCommand> {

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public NotesCommandToNotes(RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        Recipe recipe = recipeCommandToRecipe.convert(convertObject.getRecipe());

        return new Notes()
                .setId(convertObject.getId())
                .setRecipeNotes(convertObject.getRecipeNotes())
                .setRecipe(recipe);
    }
}
