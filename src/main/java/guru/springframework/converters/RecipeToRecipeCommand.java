package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<RecipeCommand, Recipe> {

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe convertObject) {
        return null;
    }
}
