package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredientsPageByRecipeId(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));

        return "recipe/ingredients/show";
    }

    @GetMapping("recipe/{id}/ingredient/new")
    public String getNewIngredientPage(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitOfMeasureCommand());

        return "recipe/ingredients/ingredient-form";
    }


    @GetMapping("ingredient/{id}/update")
    public String getUpdateIngredientPage(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredientById(id));
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitOfMeasureCommand());

        return "recipe/ingredients/ingredient-form";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long id,
                                   @PathVariable Long recipeId, Model model) {
        ingredientService.deleteIngredientById(id);
        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));

        return "recipe/ingredients/show";
    }

    @PostMapping("recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@ModelAttribute IngredientCommand ingredientCommand, Model model) {
        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        model.addAttribute("ingredient", ingredientCommand);

        return  "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredients";
    }



    //todo: add ingredient
    //todo: update ingredient
    //todo: delete ingredient
    //todo: show ingredient

}
