package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.services.IngredientService;
import guru.springframework.services.IngredientServiceImpl;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredientsPageByRecipeId(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));

        return "recipe/ingredients/show";
    }

    @GetMapping("recipe/{id}/ingredient/new")
    public String getNewIngredientPage(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", new IngredientCommand());

        return "recipe/ingredients/ingredient-form";
    }


    @GetMapping("ingredient/{id}/update")
    public String getUpdateIngredientPage(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredientById(id));

        return "recipe/ingredients/ingredient-form";
    }

    @PostMapping("recipe/{id}/ingredient/new")
    public String addNewIngredient(@PathVariable Long id,
                                   @ModelAttribute IngredientCommand ingredientCommand, Model model) {
        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        model.addAttribute("ingredient", ingredientCommand);

        return  "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredients";
    }

    //todo: add ingredient
    //todo: update ingredient
    //todo: delete ingredient
    //todo: show ingredient

}
