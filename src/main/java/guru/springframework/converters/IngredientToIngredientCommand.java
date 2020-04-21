package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureToUnitOfMeasureCommand uopCommandToUop;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uopCommandToUop,
                                         RecipeToRecipeCommand recipeToRecipeCommand) {
        this.uopCommandToUop = uopCommandToUop;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient convertObject) {

        if (convertObject == null) {
            return null;
        }

        RecipeCommand recipeCommand = recipeToRecipeCommand
                .convert(convertObject.getRecipe());

        UnitOfMeasureCommand uopCommand = uopCommandToUop
                .convert(convertObject.getUop());

        return new IngredientCommand()
                .setId(convertObject.getId())
                .setAmount(convertObject.getAmount())
                .setDescription(convertObject.getDescription())
                .setUop(uopCommand)
                .setRecipe(recipeCommand);
    }
}
