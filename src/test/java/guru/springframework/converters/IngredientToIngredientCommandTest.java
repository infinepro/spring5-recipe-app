package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class IngredientToIngredientCommandTest {

    final Long recipeId = 12L;
    final Long INGREDIENT_ID = 33L;
    final BigDecimal INGREDIENT_AMOUNT = new BigDecimal(55);
    final String INGREDIENT_DESCRIPTION = "test description";
    final Long UNIT_OF_MEASURE_ID = 77L;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        testIngredient = new Ingredient()
                .setId(INGREDIENT_ID)
                .setAmount(INGREDIENT_AMOUNT)
                .setDescription(INGREDIENT_DESCRIPTION)
                .setUom(null)
                .setRecipe(new Recipe().setId(recipeId));
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }

    @Test
    void testConvertIfUopIsNull() {
        //setup data
        testIngredient.setUom(null);

        //convert
        IngredientCommand convertIngredientCommand = ingredientToIngredientCommand.convert(testIngredient);

        //check
        Assertions.assertEquals(convertIngredientCommand.getDescription(), testIngredient.getDescription());
        Assertions.assertNull(convertIngredientCommand.getUom());
    }

    @Test
    void testConvertIngredientCommandToIngredient() {
        //setup data
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
                .setId(UNIT_OF_MEASURE_ID);

        testIngredient.setUom(unitOfMeasure);

        //convert
        IngredientCommand convertIngredientCommand = ingredientToIngredientCommand.convert(testIngredient);

        //check
        Assertions.assertEquals(convertIngredientCommand.getId(), testIngredient.getId());
        Assertions.assertEquals(convertIngredientCommand.getAmount(), testIngredient.getAmount());
        Assertions.assertEquals(convertIngredientCommand.getDescription(), testIngredient.getDescription());
        Assertions.assertEquals(convertIngredientCommand.getUom().getId(), testIngredient.getUom().getId());

    }
}