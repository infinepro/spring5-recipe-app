package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    final Long INGREDIENT_ID = 33L;
    final BigDecimal INGREDIENT_AMOUNT = new BigDecimal(55);
    final String INGREDIENT_DESCRIPTION = "test description";
    final Long UNIT_OF_MEASURE_ID = 77L;

    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientCommand testIngredientCommand;

    @BeforeEach
    void setUp() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

        testIngredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID)
                .setAmount(INGREDIENT_AMOUNT)
                .setDescription(INGREDIENT_DESCRIPTION);
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    void testConvertifUopCommandIsNull () {
        //setup data
        testIngredientCommand.setUop(null);

        //convert
        Ingredient convertIngredient = ingredientCommandToIngredient.convert(testIngredientCommand);

        //check
        Assertions.assertEquals(convertIngredient.getDescription(), testIngredientCommand.getDescription());
        Assertions.assertNull(convertIngredient.getUop());
    }

    @Test
    void testConvertIngredientCommandToIngredient() {
        //setup data
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand()
                .setId(UNIT_OF_MEASURE_ID);

        testIngredientCommand.setUop(unitOfMeasureCommand);

        //convert
        Ingredient convertIngredient = ingredientCommandToIngredient.convert(testIngredientCommand);

        //check
        Assertions.assertEquals(convertIngredient.getId(), testIngredientCommand.getId());
        Assertions.assertEquals(convertIngredient.getAmount(), testIngredientCommand.getAmount());
        Assertions.assertEquals(convertIngredient.getDescription(), testIngredientCommand.getDescription());
        Assertions.assertEquals(convertIngredient.getUop().getId(), testIngredientCommand.getUop().getId());

    }
}