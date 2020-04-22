package guru.springframework.services;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecipeServiceIT {

    final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {

    }

    @Transactional
    @Test
    public void testSafeRecipeOnDescription() {
        //take instance
        Iterable<Recipe> recipes = recipeRepository.findAll();

        if (!recipes.iterator().hasNext()) {
            throw new NullPointerException("No recipes in DB");
        }

        //transform
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);

        //do operation
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //check result
        Assertions.assertEquals(NEW_DESCRIPTION, saveRecipeCommand.getDescription());
        Assertions.assertEquals(testRecipe.getId(), saveRecipeCommand.getId());
        Assertions.assertEquals(testRecipe.getIngredients().size(), saveRecipeCommand.getIngredients().size());
        Assertions.assertEquals(testRecipe.getCategories().size(), saveRecipeCommand.getCategories().size());
    }
}
