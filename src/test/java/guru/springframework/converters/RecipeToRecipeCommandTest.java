package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class RecipeToRecipeCommandTest {


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

    RecipeToRecipeCommand recipeToRecipeCommand;
    Recipe testRecipe;

    @BeforeEach
    void setUp() {
        recipeToRecipeCommand = new RecipeToRecipeCommand(
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand(),
                new CategoryToCategoryCommand());

        testRecipe = new Recipe()
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
        testRecipe = null;
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    void testConvertToRecipeWithEmptyIngredientsAndCategories() {
        //convert
        RecipeCommand convertRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //check
        Assertions.assertEquals(convertRecipeCommand.getId(), testRecipe.getId());
        Assertions.assertNotNull(convertRecipeCommand.getIngredients());
        Assertions.assertNotNull(convertRecipeCommand.getCategories());
    }

    @Test
    void testConvertRecipeCommandToRecipe() {
        //setup data
        Notes notes = new Notes().setId(NOTES_ID);

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category().setId(CATEGORY_ID_1));
        categorySet.add(new Category().setId(CATEGORY_ID_2));

        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(new Ingredient().setId(INGREDIENT_ID_1));
        ingredientSet.add(new Ingredient().setId(INGREDIENT_ID_2));

        testRecipe
                .setCategories(categorySet)
                .setIngredients(ingredientSet)
                .setNotes(notes);

        //convert
        RecipeCommand convertRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //check
        Assertions.assertEquals(convertRecipeCommand.getId(), testRecipe.getId());
        Assertions.assertEquals(convertRecipeCommand.getDescription(), testRecipe.getDescription());
        Assertions.assertEquals(convertRecipeCommand.getPrepTime(), testRecipe.getPrepTime());
        Assertions.assertEquals(convertRecipeCommand.getCookTime(), testRecipe.getCookTime());
        Assertions.assertEquals(convertRecipeCommand.getUrl(), testRecipe.getUrl());
        Assertions.assertEquals(convertRecipeCommand.getSource(), testRecipe.getSource());
        Assertions.assertEquals(convertRecipeCommand.getDifficulty(), testRecipe.getDifficulty());
        Assertions.assertEquals(convertRecipeCommand.getServings(), testRecipe.getServings());
        Assertions.assertEquals(convertRecipeCommand.getDirections(), testRecipe.getDirections());
        Assertions.assertEquals(convertRecipeCommand.getNotes().getId(), testRecipe.getNotes().getId());

        Assertions.assertEquals(convertRecipeCommand.getIngredients().size(), testRecipe.getIngredients().size());
        Assertions.assertEquals(convertRecipeCommand.getCategories().size(), testRecipe.getCategories().size());
    }


}