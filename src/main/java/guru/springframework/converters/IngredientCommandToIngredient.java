package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom) {
        this.uomCommandToUom = uomCommandToUom;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        UnitOfMeasure unitOfMeasure = uomCommandToUom.
                convert(convertObject.getUom());

        return new Ingredient()
                .setId(convertObject.getId())
                .setUom(unitOfMeasure)
                .setAmount(convertObject.getAmount())
                .setDescription(convertObject.getDescription());

    }
}
