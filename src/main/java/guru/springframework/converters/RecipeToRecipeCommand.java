package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
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

        Set<CategoryCommand> categories = convertObject.getCategories()
                .stream()
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());

        Set<IngredientCommand> ingredients = convertObject.getIngredients()
                .stream()
                .map(ingredientToIngredientCommand::convert)
                .collect(Collectors.toSet());

        return new RecipeCommand()
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
