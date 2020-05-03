package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void testGetRecipePageById() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/show/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    void testGetNewRecipePage() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipe-form"));
    }

    @Test
    void testGetUpdateRecipePage() throws Exception {
        Long testId = 2L;
        RecipeCommand recipeCommand = new RecipeCommand()
                .setId(testId);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + testId + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostSaveOrUpdateRecipe() throws Exception {
        Long testId = 2L;
        String testDescription = "some description";
        RecipeCommand recipeCommand = new RecipeCommand()
                .setId(testId)
                .setDescription(testDescription);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", testId.toString())
                .param("description", "some description"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/" + testId));
    }

    @Test
    void testDeleteRecipeById() throws Exception {
        long testId = 3L;

        mockMvc.perform(get("/recipe/" + testId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));


        verify(recipeService, times(1)).deleteRecipeById(anyLong());
    }

    @Test
    void handleNumberFormat() {

    }

}
