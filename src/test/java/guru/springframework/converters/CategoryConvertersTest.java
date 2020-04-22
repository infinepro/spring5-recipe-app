package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryConvertersTest {

    final Long CATEGORY_ID = 11L;
    final String CATEGORY_DESCRIPTION = "Some description";
    final Long RECIPE_ID = 33L;

    Recipe recipe;
    RecipeCommand recipeCommand;
    Set<Recipe> recipes;
    Set<RecipeCommand> recipesCommand;
    Category testCategory;
    CategoryCommand testCategoryCommand;

    CategoryToCategoryCommand categoryToCategoryCommand;
    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory(new RecipeCommandToRecipe(new IngredientCommandToIngredient(),
                new NotesCommandToNotes(),
                categoryCommandToCategory));
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(categoryCommandToCategory.convert(null));
        Assertions.assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(categoryToCategoryCommand.convert(new Category()));
        Assertions.assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void testConvertCategoryToCommandCategory() {
        //start data
        recipe = new Recipe().setId(RECIPE_ID);
        recipes = new HashSet<>();
        recipes.add(recipe);

        testCategory = new Category()
                .setId(CATEGORY_ID)
                .setDescription(CATEGORY_DESCRIPTION)
                .setRecipes(recipes);

        //convert
        CategoryCommand convertedCategoryCommand = categoryToCategoryCommand.convert(testCategory);

        //asserts
        Assertions.assertEquals(testCategory.getId(), convertedCategoryCommand.getId());
        Assertions.assertEquals(testCategory.getDescription(), convertedCategoryCommand.getDescription());

        long recipeIdBefore = testCategory.getRecipes().iterator().next().getId();
        long recipeCommandIdAfter = testCategoryCommand.getRecipes().iterator().next().getId();
        Assertions.assertEquals(recipeIdBefore, recipeCommandIdAfter);

    }

    @Test
    void testConvertCategoryCommandToCategory() {
        //start data
        recipeCommand = new RecipeCommand().setId(RECIPE_ID);
        recipesCommand = new HashSet<>();
        recipesCommand.add(recipeCommand);

        testCategoryCommand = new CategoryCommand()
                .setId(CATEGORY_ID)
                .setDescription(CATEGORY_DESCRIPTION)
                .setRecipes(recipesCommand);

        //convert
        Category convertedCategory = categoryCommandToCategory.convert(testCategoryCommand);

        //asserts
        Assertions.assertEquals(convertedCategory.getId(), testCategoryCommand.getId());
        Assertions.assertEquals(convertedCategory.getDescription(), testCategoryCommand.getDescription());

        long recipeCommandIdBefore = testCategoryCommand.getRecipes().iterator().next().getId();
        long recipeIdAfter = convertedCategory.getRecipes().iterator().next().getId();
        Assertions.assertEquals(recipeCommandIdBefore, recipeIdAfter);

    }
}