package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
