package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        Optional<Recipe> emptyOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(emptyOptional);

        Assertions.assertThrows(RuntimeException.class, () -> ingredientService.saveIngredientCommand(any()));
        verify()
    }

    @Test
    void testSaveIngredientCommandIfUnitOfMeasureNotFound() {

        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description");

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        Optional<UnitOfMeasure> emptyOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(emptyOptional);


        Assertions.assertThrows(RuntimeException.class, () -> ingredientService.saveIngredientCommand(any()));
    }

    @Test
    void testSaveIngredientCommandIfIngredientUpdate() {

        //set up
        final Long INGREDIENT_ID = 3L;

        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient().setId(INGREDIENT_ID));

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
                    .setId(4L)
                    .setDescription("som description");

        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description")
                .setIngredients(ingredients);


        Optional<Recipe> recipeOptional = Optional.of(recipe);
        Optional<UnitOfMeasure> uomOptional = Optional.of(unitOfMeasure);

        //mock
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(uomOptional);

        //check result
        Assertions.assertEquals(ingredientService.saveIngredientCommand(ingredientCommand).getId(), INGREDIENT_ID);
        verify(recipe.getIngredients(), times(0)).add(any());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    void testSaveIngredientCommandIfAddNewIngredient() {

        //set up
        final Long INGREDIENT_ID = 3L;

        IngredientCommand ingredientCommand = new IngredientCommand()
                .setId(INGREDIENT_ID);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
                .setId(4L)
                .setDescription("som description");

        Recipe recipe = new Recipe()
                .setId(10L)
                .setDescription("some description");


        Optional<Recipe> recipeOptional = Optional.of(recipe);
        Optional<UnitOfMeasure> uomOptional = Optional.of(unitOfMeasure);

        //mock
        when(recipeRepository.findById(any())).thenReturn(recipeOptional);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(uomOptional);

        //check result
        Assertions.assertEquals(ingredientService.saveIngredientCommand(any()).getId(), INGREDIENT_ID);
        verify(recipe.getIngredients(), times(1)).add(any());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    void testGetIngredientByIdIfNotFound() {
        Optional<Ingredient> emptyOptional = Optional.empty();

        when(ingredientRepository.findById(anyLong())).thenReturn(emptyOptional);

        Assertions.assertNotNull(ingredientService.getIngredientById(anyLong()));
        Assertions.assertNull(ingredientService.getIngredientById(anyLong()).getId());
    }

    @Test
    void testGetIngredientByIdIfExist() {
        Long testId = 3L;
        Ingredient ingredient = new Ingredient()
                .setId(testId);
        Optional<Ingredient> testOptional = Optional.of(ingredient);

        when(ingredientRepository.findById(anyLong())).thenReturn(testOptional);

        Assertions.assertNotNull(ingredientService.getIngredientById(anyLong()));
        Assertions.assertEquals(ingredientService.getIngredientById(anyLong()).getId(), testId);
    }
}