package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(ExceptionHandlerController.class)
                .build();
    }

    @Test
    void testGetIngredientsPageByRecipeId() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + recipeCommand.getId() + "/ingredients"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/show"));
    }

    @Test
    void testGetNewIngredientPage() throws Exception {
        long recipeId = 3L;
        List<UnitOfMeasureCommand> uopList = new ArrayList<>();

        when(unitOfMeasureService.getAllUnitOfMeasureCommand()).thenReturn(uopList);

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredients/ingredient-form"));

    }

    @Test
    void testGetUpdateIngredientPage() throws Exception {
        long ingredientId = 3L;
        List<UnitOfMeasureCommand> uopList = new ArrayList<>();

        when(ingredientService.getIngredientById(anyLong())).thenReturn(new IngredientCommand());
        when(unitOfMeasureService.getAllUnitOfMeasureCommand()).thenReturn(uopList);

        mockMvc.perform(get("/ingredient/" + ingredientId + "/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredients/ingredient-form"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        long recipeId = 3L;
        long ingredientId = 5L;

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(new RecipeCommand().setId(recipeId));

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/ingredients/show"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void testAddNewIngredient() throws Exception {
        Long recipeId = 3L;
        Long ingredientId = 5L;

        when(ingredientService.saveIngredientCommand(any()))
                .thenReturn(new IngredientCommand().setId(ingredientId).setRecipeId(recipeId));

        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient/new")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", ingredientId.toString())
                    .param("recipeId", recipeId.toString()))

                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));

        verify(ingredientService, times(1)).saveIngredientCommand(any());

    }
}