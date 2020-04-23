package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class RecipeCommandToRecipeTest {

    final Long RECIPE_ID = 1L;
    final String RECIPE_DESCRIPTION = "some Recipe";
    final Integer PREP_TIME = 2;
    final Integer COOK_TIME = 3;
    final Integer SERVINGS = 4;
    final String SOURCE = "some source";
    final String URL = "some url";
    final String DIRECTIONS = "some directions";
    final Difficulty DIFFICULTY = Difficulty.EASY;
    final Long NOTES_ID = 10L;
    final Long INGREDIENT_ID_1 = 21L;
    final Long INGREDIENT_ID_2 = 22L;
    final Long CATEGORY_ID_1 = 31L;
    final Long CATEGORY_ID_2 = 32L;

    RecipeCommandToRecipe recipeCommandToRecipe;
    RecipeCommand testRecipeCommand;

    @BeforeEach
    void setUp() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes(),
                new CategoryCommandToCategory());

        testRecipeCommand = new RecipeCommand()
                .setId(RECIPE_ID)
                .setDescription(RECIPE_DESCRIPTION)
                .setPrepTime(PREP_TIME)
                .setCookTime(COOK_TIME)
                .setServings(SERVINGS)
                .setSource(SOURCE)
                .setUrl(URL)
                .setDirections(DIRECTIONS)
                .setDifficulty(DIFFICULTY);
    }

    @AfterEach
    void clearRecipe() {
        testRecipeCommand = null;
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    void testConvertWithNullIngredientCommandAndCategoryCommandAndNotesCommand() {
        //setup data
        testRecipeCommand
                .setNotes(null)
                .setCategories(null)
                .setIngredients(null);

        //convert
        Recipe convertRecipe = recipeCommandToRecipe.convert(testRecipeCommand);

        //check
        Assertions.assertEquals(convertRecipe.getId(), testRecipeCommand.getId());
        Assertions.assertNull(convertRecipe.getIngredients());
        Assertions.assertNull(convertRecipe.getCategories());
        Assertions.assertNull(convertRecipe.getNotes());

    }

    @Test
    void testConvertToRecipeWithEmptyIngredientsAndCategoriesAndNotes() {
        //setup data
        testRecipeCommand
                .setNotes(new NotesCommand())
                .setCategories(new HashSet<CategoryCommand>())
                .setIngredients(new HashSet<IngredientCommand>());

        //convert
        Recipe convertRecipe = recipeCommandToRecipe.convert(testRecipeCommand);

        //check
        Assertions.assertEquals(convertRecipe.getId(), testRecipeCommand.getId());
        Assertions.assertNotNull(convertRecipe.getIngredients());
        Assertions.assertNotNull(convertRecipe.getCategories());
        Assertions.assertNotNull(convertRecipe.getNotes());
    }

    @Test
    void testConvertRecipeCommandToRecipe() {
        //setup data
        NotesCommand notesCommand = new NotesCommand().setId(NOTES_ID);

        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        categoryCommandSet.add(new CategoryCommand().setId(CATEGORY_ID_1));
        categoryCommandSet.add(new CategoryCommand().setId(CATEGORY_ID_2));

        Set<IngredientCommand> ingredientCommandSet = new HashSet<>();
        ingredientCommandSet.add(new IngredientCommand().setId(INGREDIENT_ID_1));
        ingredientCommandSet.add(new IngredientCommand().setId(INGREDIENT_ID_2));

        testRecipeCommand
                .setCategories(categoryCommandSet)
                .setIngredients(ingredientCommandSet);

        //convert
        Recipe convertRecipe = recipeCommandToRecipe.convert(testRecipeCommand);

        //check
        Assertions.assertEquals(convertRecipe.getId(), testRecipeCommand.getId());
        Assertions.assertEquals(convertRecipe.getDescription(), testRecipeCommand.getDescription());
        Assertions.assertEquals(convertRecipe.getPrepTime(), testRecipeCommand.getPrepTime());
        Assertions.assertEquals(convertRecipe.getCookTime(), testRecipeCommand.getCookTime());
        Assertions.assertEquals(convertRecipe.getUrl(), testRecipeCommand.getUrl());
        Assertions.assertEquals(convertRecipe.getSource(), testRecipeCommand.getSource());
        Assertions.assertEquals(convertRecipe.getDifficulty(), testRecipeCommand.getDifficulty());
        Assertions.assertEquals(convertRecipe.getServings(), testRecipeCommand.getServings());
        Assertions.assertEquals(convertRecipe.getDirections(), testRecipeCommand.getDirections());
        Assertions.assertEquals(convertRecipe.getNotes().getId(), testRecipeCommand.getNotes().getRecipeNotes());

        Assertions.assertEquals(convertRecipe.getIngredients().size(), testRecipeCommand.getIngredients().size());
        Assertions.assertEquals(convertRecipe.getCategories().size(), testRecipeCommand.getCategories().size());

    }


}