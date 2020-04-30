package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(  ingredientRepository,
                                                        ingredientCommandToIngredient,
                                                        recipeRepository,
                                                        unitOfMeasureRepository,
                                                        ingredientToIngredientCommand);
    }

    @Test
    void testSaveIngredientCommandIfRecipeNotFound() {
        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(1L)
                .setRecipeId(2L); //for logging ing method, else NPE

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(RuntimeException.class, () -> ingredientService.saveIngredientCommand(ingredientCommand));
        assertNotNull(throwable.getMessage());
    }

    @Test
    void testSaveIngredientCommandIfUnitOfMeasureNotFound() {
        Long INGREDIENT_ID = 1L;

        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID)
                .setRecipeId(2L)
                .setUom(new UnitOfMeasureCommand().setId(3L)); //for logging ing method, else NPE

        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description");

        recipe.getIngredients().add(new Ingredient().setId(INGREDIENT_ID));

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(RuntimeException.class, () -> ingredientService.saveIngredientCommand(ingredientCommand));
        assertNotNull(throwable.getMessage());
    }

    @Test
    void testSaveIngredientCommandIfIngredientUpdate() {

        //set up
        final Long INGREDIENT_ID = 3L;

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand()
                    .setId(4L)
                    .setDescription("some description");

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
                .setId(3L)
                .setDescription("some description");

        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID)
                .setRecipeId(10L)
                .setUom(unitOfMeasureCommand);

        Ingredient ingredient = new Ingredient()
                .setId(INGREDIENT_ID);


        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description");
                recipe.getIngredients().add(ingredient);

        //mock
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        when(recipeRepository.save(any())).thenReturn(recipe);

        //check result
        assertEquals(ingredientService.saveIngredientCommand(ingredientCommand).getId(), INGREDIENT_ID);
        assertEquals(recipe.getIngredients().size(), 1L);
    }

    @Test
    void testSaveIngredientCommandIfAddNewIngredient() {

        //set up
        final Long INGREDIENT_ID = 3L;

        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID)
                .setRecipeId(10L);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
                .setId(4L)
                .setDescription("som description");

        Ingredient ingredient = new Ingredient()
                .setId(INGREDIENT_ID)
                .setUom(unitOfMeasure);

        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description");
        recipe.getIngredients().add(new Ingredient().setId(3333L));

        //mock
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        when(recipeRepository.save(any())).thenReturn(recipe);

        //check result
        assertEquals(ingredientService.saveIngredientCommand(ingredientCommand).getId(), INGREDIENT_ID);
        assertEquals(recipe.getIngredients().size(), 2L);
    }

    @Test
    void testGetIngredientByIdIfNotFound() {
        Optional<Ingredient> emptyOptional = Optional.empty();

        when(ingredientRepository.findById(anyLong())).thenReturn(emptyOptional);

        assertNotNull(ingredientService.getIngredientById(anyLong()));
        assertNull(ingredientService.getIngredientById(anyLong()).getId());
    }

    @Test
    void testGetIngredientByIdIfExist() {
        Long testId = 3L;
        Ingredient ingredient = new Ingredient()
                .setId(testId);
        Optional<Ingredient> testOptional = Optional.of(ingredient);

        when(ingredientRepository.findById(anyLong())).thenReturn(testOptional);

        assertNotNull(ingredientService.getIngredientById(anyLong()));
        assertEquals(ingredientService.getIngredientById(anyLong()).getId(), testId);
    }
}