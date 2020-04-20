package guru.springframework.controllers;

import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IndexController.class)
class IndexControllerTest {

    RecipeService recipeService;

    //@Mock
    //RecipeRepository recipeRepository;

    @Autowired
    MockMvc mockMvc;

    String indexView;

    @BeforeEach
    void setUp() {
      //  recipeService = new RecipeServiceImpl(recipeRepository);
        //indexView = "index";
    }

    @Test
    void getIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(indexView));
    }
}