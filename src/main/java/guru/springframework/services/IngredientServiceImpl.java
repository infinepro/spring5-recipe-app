package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    private Optional<Ingredient> findIngredientInRecipe(Recipe recipe, Long ingredientId) {
        return recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();
    }

    private void updateIngredientInRecipe(Ingredient ingredient, IngredientCommand ingredientCommand) {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository
                .findById(ingredient.getUop().getId());

        if (unitOfMeasureOptional.isPresent()) {
            UnitOfMeasure unitOfMeasure = unitOfMeasureOptional.get();

            ingredient.setDescription(ingredientCommand.getDescription())
                    .setAmount(ingredientCommand.getAmount())
                    .setUop(unitOfMeasure);
        } else {
            throw new RuntimeException("Unit of measure not found");
        }
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = findIngredientInRecipe(recipe, ingredientCommand.getId());

            if (ingredientOptional.isPresent()) {
                //if ingredient founded then update it
                Ingredient ingredient = ingredientOptional.get();

                updateIngredientInRecipe(ingredient, ingredientCommand);
            } else {
                //if ingredient not found then add new
                recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe recipeSave = recipeRepository.save(recipe);

            //return saved ingredientCommand in recipeSave
            return ingredientToIngredientCommand
                    .convert(findIngredientInRecipe(recipeSave, ingredientCommand.getId()).get());
        } else {
            //throw new RuntimeException("recipe not found, id:" + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }
    }

    @Override
    public IngredientCommand getIngredientById(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);

        return ingredientToIngredientCommand
                .convert(ingredientOptional.orElseGet(Ingredient::new));
    }

    @Override
    public void deleteIngredientById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
