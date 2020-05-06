package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {


    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(ExceptionHandlerController.class)
                .build();
    }

    @Test
    void testGetUploadImageForm() throws Exception {
        long recipeId = 3L;

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(new RecipeCommand().setId(recipeId));

        mockMvc.perform(get("/recipe/" + recipeId + "/image/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/image/image-form"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void getUploadImageForm() throws Exception {
        long recipeId = 3L;

        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile",
                        "testing.txt",
                        "text/plain",
                        "some text file".getBytes());

        mockMvc.perform(multipart("/recipe/" + recipeId + "/image/new").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/" + recipeId));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }
}