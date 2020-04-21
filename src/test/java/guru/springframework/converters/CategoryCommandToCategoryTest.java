package guru.springframework.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ConvertersDomainToCommandTest {

    @Autowired
    CategoryToCategoryCommand categoryToCategoryCommand;

    @Autowired
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
    }

    @Test
    void convert() {
    }
}