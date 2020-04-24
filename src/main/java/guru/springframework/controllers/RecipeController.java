package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipe/show/{id}")
    public String getRecipe(@PathVariable Long id, Model model) {

        model.addAttribute("recipe", recipeService.getRecipeById(id));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/add-recipe-form";
    }


    @PostMapping("recipe/new")
    public String saveOrUpdateNewRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + saveRecipeCommand.getId();
    }
}
