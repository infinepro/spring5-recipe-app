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
    public String getRecipePageById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));
        log.info("Get recipe for user with ID: " + id);

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipePage(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipe-form";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipePage(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));

        return "recipe/recipe-form";
    }


    @PostMapping("recipe/new")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        log.info("Add or update recipe with ID: " + saveRecipeCommand.getId());

        return "redirect:/recipe/show/" + saveRecipeCommand.getId();
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        log.info("Recipe with ID: " + id + " deleted");

        return "redirect:/index";
    }

}
