package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeToRecipeCommand recipeToRecipeCommand;
    private RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>)recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException();
        }

        return recipeOptional.get();
    }

    @Transactional
    @Override
    public RecipeCommand getRecipeCommandById(long id) {
        Recipe recipe = getRecipeById(id);

        return recipeToRecipeCommand.convert(recipe);
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe saveRecipe = recipeRepository.save(detachedRecipe);

        log.info("Recipe save with id:" + saveRecipe.getId());

        return recipeToRecipeCommand.convert(saveRecipe);
    }

    @Transactional
    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}
