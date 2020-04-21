package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<Recipe, RecipeCommand> {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        Notes notes = notesCommandToNotes.convert(convertObject.getNotes());

        Set<Category> categories = convertObject.getCategories()
                .stream()
                .map(categoryCommandToCategory::convert)
                .collect(Collectors.toSet());

        Set<Ingredient> ingredients = convertObject.getIngredients()
                .stream()
                .map(ingredientCommandToIngredient::convert)
                .collect(Collectors.toSet());

        return new Recipe()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription())
                .setSource(convertObject.getSource())
                .setUrl(convertObject.getUrl())
                .setServings(convertObject.getServings())
                .setPrepTime(convertObject.getPrepTime())
                .setCookTime(convertObject.getCookTime())
                .setDirections(convertObject.getDirections())
                .setDifficulty(convertObject.getDifficulty())
                .setImage(convertObject.getImage())
                .setNotes(notes)
                .setIngredients(ingredients)
                .setCategories(categories);

    }
}
