package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryCommandToCategory implements Converter<Category, CategoryCommand> {

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public CategoryCommandToCategory(RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        Set<Recipe> recipes = convertObject.getRecipes()
                .stream()
                .map(recipeCommandToRecipe::convert)
                .collect(Collectors.toSet());

        return new Category()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription())
                .setRecipes(recipes);

    }
}
