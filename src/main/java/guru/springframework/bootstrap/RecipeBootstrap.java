package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasure ounce;
    private UnitOfMeasure pound;
    private UnitOfMeasure piece;
    private UnitOfMeasure tablespoon;


    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;

        if (unitOfMeasureRepository.count() == 0L) {
            //init unit of measure
            Optional<UnitOfMeasure> pieceOpt = unitOfMeasureRepository.findByDescription("piece");
            if (!pieceOpt.isPresent()) {
                throw new RuntimeException();
            }

            Optional<UnitOfMeasure> ounceOpt = unitOfMeasureRepository.findByDescription("ounce");
            if (!ounceOpt.isPresent()) {
                throw new RuntimeException();
            }

            Optional<UnitOfMeasure> poundOpt = unitOfMeasureRepository.findByDescription("pound");
            if (!poundOpt.isPresent()) {
                throw new RuntimeException();
            }

            Optional<UnitOfMeasure> tablespoonOpt = unitOfMeasureRepository.findByDescription("tablespoon");
            if (!tablespoonOpt.isPresent()) {
                throw new RuntimeException();
            }

            tablespoon = tablespoonOpt.get();
            ounce = ounceOpt.get();
            pound = poundOpt.get();
            piece = pieceOpt.get();
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("App event is happened");
        if (recipeRepository.count() != 0L) {
            log.info("Recipe data already exist");
        } else {
            recipeRepository.saveAll(initListRecipes());
            log.info("Recipes initialisation successful");
        }
    }

    private Set<Ingredient> initIngredientsForGuacamole() {

        Ingredient avocado = new Ingredient()
                .setAmount(new BigDecimal(2))
                .setDescription("Avocado")
                .setUom(piece);

        Ingredient freshCilantro = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Fresh Cilantro")
                .setUom(ounce);

        Ingredient lime = new Ingredient()
                .setAmount(new BigDecimal(0.5))
                .setDescription("Lime")
                .setUom(piece);

        Ingredient redOnion = new Ingredient()
                .setAmount(new BigDecimal(0.25))
                .setDescription("Red Onion")
                .setUom(piece);

        Ingredient serranoChiles = new Ingredient()
                .setAmount(new BigDecimal(2))
                .setDescription("Serrano Chili")
                .setUom(piece);

        Ingredient tomato = new Ingredient()
                .setAmount(new BigDecimal(0.5))
                .setDescription("Tomato")
                .setUom(piece);

        Ingredient tortillaChips = new Ingredient()
                .setAmount(new BigDecimal(0.75))
                .setDescription("Tortilla Chips")
                .setUom(pound);

        Ingredient blackPeppercorns = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Black Peppercorns")
                .setUom(ounce);

        Ingredient salt = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Salt")
                .setUom(ounce);

        Set<Ingredient> ingredientsForGuacamole = new HashSet<>();
        ingredientsForGuacamole.add(avocado);
        ingredientsForGuacamole.add(freshCilantro);
        ingredientsForGuacamole.add(lime);
        ingredientsForGuacamole.add(redOnion);
        ingredientsForGuacamole.add(serranoChiles);
        ingredientsForGuacamole.add(tomato);
        ingredientsForGuacamole.add(tortillaChips);
        ingredientsForGuacamole.add(blackPeppercorns);
        ingredientsForGuacamole.add(salt);

        return ingredientsForGuacamole;

    }

    private Set<Ingredient> initIngredientsForGrilledChicken() {

        Ingredient anchoChiliPowder = new Ingredient()
                .setAmount(new BigDecimal(2))
                .setDescription("Ancho chili povder")
                .setUom(tablespoon);

        Ingredient avocado = new Ingredient()
                .setAmount(new BigDecimal(4))
                .setDescription("Avocado")
                .setUom(piece);

        Ingredient babyArugula = new Ingredient()
                .setAmount(new BigDecimal(0.33))
                .setDescription("Baby Arugula")
                .setUom(pound);

        Ingredient cherryTomatoes = new Ingredient()
                .setAmount(new BigDecimal(0.5))
                .setDescription("Cherry Tomatoes")
                .setUom(pound);

        Ingredient cornTortillas = new Ingredient()
                .setAmount(new BigDecimal(8))
                .setDescription("Corn Tortillas")
                .setUom(piece);

        Ingredient driedOregano = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Dried Oregano")
                .setUom(ounce);

        Ingredient freshCilantro = new Ingredient()
                .setAmount(new BigDecimal(0.33))
                .setDescription("Fresh Cilantro")
                .setUom(ounce);

        Ingredient garlicHead = new Ingredient()
                .setAmount(new BigDecimal(0.25))
                .setDescription("Garlic Head")
                .setUom(piece);

        Ingredient groundCumin = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Ground Cumin")
                .setUom(ounce);

        Ingredient limes = new Ingredient()
                .setAmount(new BigDecimal(1.5))
                .setDescription("Limes")
                .setUom(piece);

        Ingredient milk = new Ingredient()
                .setAmount(new BigDecimal(2))
                .setDescription("Milk")
                .setUom(ounce);

        Ingredient oranges = new Ingredient()
                .setAmount(new BigDecimal(1.25))
                .setDescription("Oranges")
                .setUom(piece);

        Ingredient radishes = new Ingredient()
                .setAmount(new BigDecimal(3.25))
                .setDescription("Radishes")
                .setUom(ounce);

        Ingredient redOnion = new Ingredient()
                .setAmount(new BigDecimal(0.5))
                .setDescription("Red Onion")
                .setUom(piece);

        Ingredient serranoChiles = new Ingredient()
                .setAmount(new BigDecimal(2))
                .setDescription("Serrano Chiles")
                .setUom(piece);

        Ingredient skinlessBonelessChickenThighs = new Ingredient()
                .setAmount(new BigDecimal(1.25))
                .setDescription("Skinless Boneless Chicken Thighs")
                .setUom(pound);

        Ingredient sourCream = new Ingredient()
                .setAmount(new BigDecimal(0.25))
                .setDescription("Sour Cream")
                .setUom(pound);

        Ingredient tomato = new Ingredient()
                .setAmount(new BigDecimal(0.5))
                .setDescription("Tomato")
                .setUom(piece);

        Ingredient blackPeppercorns = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Black Peppercorns")
                .setUom(ounce);

        Ingredient oliveOil = new Ingredient()
                .setAmount(new BigDecimal(1))
                .setDescription("Olive Oil")
                .setUom(ounce);

        Ingredient salt = new Ingredient()
                .setAmount(new BigDecimal(0.2))
                .setDescription("Salt")
                .setUom(ounce);

        Ingredient sugar = new Ingredient()
                .setAmount(new BigDecimal(0.1))
                .setDescription("Sugar")
                .setUom(ounce);


        Set<Ingredient> ingredientsForGrilledChicken = new HashSet<>();
        ingredientsForGrilledChicken.add(anchoChiliPowder);
        ingredientsForGrilledChicken.add(avocado);
        ingredientsForGrilledChicken.add(babyArugula);
        ingredientsForGrilledChicken.add(cherryTomatoes);
        ingredientsForGrilledChicken.add(cornTortillas);
        ingredientsForGrilledChicken.add(driedOregano);
        ingredientsForGrilledChicken.add(freshCilantro);
        ingredientsForGrilledChicken.add(garlicHead);
        ingredientsForGrilledChicken.add(groundCumin);
        ingredientsForGrilledChicken.add(limes);
        ingredientsForGrilledChicken.add(milk);
        ingredientsForGrilledChicken.add(oranges);
        ingredientsForGrilledChicken.add(radishes);
        ingredientsForGrilledChicken.add(redOnion);
        ingredientsForGrilledChicken.add(serranoChiles);
        ingredientsForGrilledChicken.add(skinlessBonelessChickenThighs);
        ingredientsForGrilledChicken.add(sourCream);
        ingredientsForGrilledChicken.add(tomato);
        ingredientsForGrilledChicken.add(blackPeppercorns);
        ingredientsForGrilledChicken.add(oliveOil);
        ingredientsForGrilledChicken.add(salt);
        ingredientsForGrilledChicken.add(sugar);

        return ingredientsForGrilledChicken;
    }

    private List<Recipe> initListRecipes() {

        //init categories
        Optional<Category> americanOpt = categoryRepository.findByDescription("American");
        if (!americanOpt.isPresent()) {
            throw new RuntimeException();
        }

        Optional<Category> italianOpt = categoryRepository.findByDescription("Italian");
        if (!italianOpt.isPresent()) {
            throw new RuntimeException();
        }

        Optional<Category> mexicanOpt = categoryRepository.findByDescription("Mexican");
        if (!mexicanOpt.isPresent()) {
            throw new RuntimeException();
        }

        Optional<Category> fastFoodOpt = categoryRepository.findByDescription("Fast food");
        if (!fastFoodOpt.isPresent()) {
            throw new RuntimeException();
        }

        Category mexican = mexicanOpt.get();
        Category american = americanOpt.get();
        Category italian = italianOpt.get();
        Category fastFood = fastFoodOpt.get();


        /////init FIRST recipe
        Recipe guacamoleRecipe = new Recipe();
        {
            Notes notesForGuacamoleRecipe = new Notes()
                    .setRecipeNotes("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze" +
                            " of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or" +
                            " spoon it on top of tacos for an easy dinner upgrade.");

            Set<Recipe> recipesFirst = new HashSet<>();
            recipesFirst.add(guacamoleRecipe);

            Set<Ingredient> ingredientsForGuacamole = initIngredientsForGrilledChicken();
            for (Ingredient ingredient : ingredientsForGuacamole) {
                ingredient.setRecipe(guacamoleRecipe);
            }

            mexican.setRecipes(recipesFirst);
            guacamoleRecipe.getCategories().add(mexican);
            guacamoleRecipe.setIngredients(ingredientsForGuacamole)
                    .setCookTime(10)
                    .setDifficulty(Difficulty.EASY)
                    .setDirections("none")
                    .setNotes(notesForGuacamoleRecipe)
                    .setPrepTime(10)
                    .setServings(4)
                    .setSource("none")
                    .setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/")
                    .setDescription("How to Make Perfect Guacamole");
        }

        /////init SECOND recipe
        Recipe grilledChickenRecipe = new Recipe();
        {
            Notes notesForGrilledChickenRecipe = new Notes()
                    .setRecipeNotes("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. " +
                            "Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");

            Set<Recipe> recipesSecond = new HashSet<>();
            recipesSecond.add(grilledChickenRecipe);

            Set<Ingredient> ingredientsForGrilledChicken = initIngredientsForGrilledChicken();
            for (Ingredient ingredient : ingredientsForGrilledChicken) {
                ingredient.setRecipe(grilledChickenRecipe);
            }

            mexican.setRecipes(recipesSecond);
            grilledChickenRecipe.getCategories().add(mexican);
            grilledChickenRecipe.setIngredients(ingredientsForGrilledChicken)
                    .setCookTime(15)
                    .setDifficulty(Difficulty.EASY)
                    .setDirections("none")
                    .setNotes(notesForGrilledChickenRecipe)
                    .setPrepTime(20)
                    .setServings(5)
                    .setSource("none")
                    .setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/")
                    .setDescription("Spicy Grilled Chicken Tacos");
        }

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(guacamoleRecipe);
        recipes.add(grilledChickenRecipe);

        return recipes;
    }
}
