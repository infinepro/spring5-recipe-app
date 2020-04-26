package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientController {

    private RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredientsPageByRecipeId(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));

        return "recipe/ingredients/show";
    }


    //todo: add ingredient
    //todo: update ingredient
    //todo: delete ingredient
    //todo: show ingredient

}
