package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<RecipeCommand, Recipe> {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 NotesToNotesCommand notesToNotesCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe convertObject) {

        if (convertObject == null) {
            return null;
        }

        NotesCommand notes = notesToNotesCommand.convert(convertObject.getNotes());

        final RecipeCommand recipeCommand = new RecipeCommand()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription())
                .setSource(convertObject.getSource())
                .setUrl(convertObject.getUrl())
                .setServings(convertObject.getServings())
                .setPrepTime(convertObject.getPrepTime())
                .setCookTime(convertObject.getCookTime())
                .setDirections(convertObject.getDirections())
                .setDifficulty(convertObject.getDifficulty())
                .setNotes(notes)
                .setImage(convertObject.getImage());


        if (convertObject.getCategories() != null && convertObject.getCategories().size() > 0) {
            convertObject.getCategories().forEach(
                    category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        if (convertObject.getIngredients() != null) {
            convertObject.getIngredients().forEach(
                    ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
