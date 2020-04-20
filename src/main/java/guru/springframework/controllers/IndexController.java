package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        log.info("Index page is called on url '/'");
        //log.info("UOM ID is " + unitOfMeasureRepository.findByDescription("teaspoon").get().getId());

        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "index";
    }
}
