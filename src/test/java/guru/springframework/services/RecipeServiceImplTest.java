package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe );
    }

    @Test
    void getRecipeById() {
        Recipe testRecipe = new Recipe();
        testRecipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(testRecipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Assertions.assertEquals(recipeService.getRecipeById(1L).getId(), 1);
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllRecipes() {
        List<Recipe> testRecipes = new ArrayList<>();
        Recipe testRecipe = new Recipe();
        testRecipe.setId(1L);
        testRecipes.add(testRecipe);

        when(recipeRepository.findAll()).thenReturn(testRecipes);
        Assertions.assertEquals(recipeService.getAllRecipes().size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}