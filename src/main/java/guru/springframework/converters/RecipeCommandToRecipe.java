package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<Recipe, RecipeCommand> {

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand convertObject) {
        return null;
    }
}
