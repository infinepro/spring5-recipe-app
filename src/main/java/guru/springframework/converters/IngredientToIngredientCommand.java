package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureToUnitOfMeasureCommand uopCommandToUop;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uopCommandToUop) {
        this.uopCommandToUop = uopCommandToUop;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient convertObject) {

        if (convertObject == null) {
            return null;
        }

        Long recipeId;

        if (convertObject.getRecipe() == null || convertObject.getRecipe().getId() == null) {
            recipeId = null;
        } else {
            recipeId = convertObject.getRecipe().getId();
        }

        UnitOfMeasureCommand uopCommand = uopCommandToUop
                .convert(convertObject.getUom());

        return new IngredientCommand()
                .setId(convertObject.getId())
                .setAmount(convertObject.getAmount())
                .setDescription(convertObject.getDescription())
                .setUom(uopCommand)
                .setRecipeId(recipeId);
    }
}
