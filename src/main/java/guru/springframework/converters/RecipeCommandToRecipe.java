package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

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

        final Recipe recipe = new Recipe()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription())
                .setSource(convertObject.getSource())
                .setUrl(convertObject.getUrl())
                .setServings(convertObject.getServings())
                .setPrepTime(convertObject.getPrepTime())
                .setCookTime(convertObject.getCookTime())
                .setDirections(convertObject.getDirections())
                .setDifficulty(convertObject.getDifficulty())
                .setNotes(notes);

        if (convertObject.getCategories() != null && convertObject.getCategories().size() > 0) {
            convertObject.getCategories()
                    .forEach(category ->recipe.getCategories().add(categoryCommandToCategory.convert(category)));
        }

        if (convertObject.getIngredients() != null && convertObject.getIngredients().size() > 0) {
            convertObject.getIngredients().forEach(
                    ingredient -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));

        }

        return recipe;
    }
}
