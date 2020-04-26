package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(long id);

    @Transactional
    RecipeCommand getRecipeCommandById(long id);

    @Transactional
    RecipeCommand saveRecipeCommand(RecipeCommand command);

    @Transactional
    void deleteRecipeById(Long id);
}
