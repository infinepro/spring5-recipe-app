package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
    import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

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

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        log.info("Recipe with ID: " + id + " deleted");

        return "redirect:/index";
    }

    @PostMapping("recipe/new")
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());

            return "recipe/recipe-form";
        } else {
            RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
            log.info("Add or update recipe with ID: " + saveRecipeCommand.getId());

            return "redirect:/recipe/show/" + saveRecipeCommand.getId();
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error404");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }


}
