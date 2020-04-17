package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.warn("App event is happened");
        initRecipes();
        log.warn("Recipes initialisation successful");
    }

    private Set<Ingredient> initIngredientsForGuacamole() {
        Optional<UnitOfMeasure> pieceOpt = unitOfMeasureRepository.findByDescription("piece");

        if (!pieceOpt.isPresent()) {
            throw new RuntimeException();
        }

        UnitOfMeasure piece = pieceOpt.get();

        Optional<UnitOfMeasure> ounceOpt = unitOfMeasureRepository.findByDescription("ounce");

        if (!ounceOpt.isPresent()) {
            throw new RuntimeException();
        }

        UnitOfMeasure ounce = ounceOpt.get();

        Optional<UnitOfMeasure> poundOpt = unitOfMeasureRepository.findByDescription("pound");

        if (!poundOpt.isPresent()) {
            throw new RuntimeException();
        }

        UnitOfMeasure pound = poundOpt.get();

        //avocado
        Ingredient avocado = new Ingredient();
        avocado.setAmount(new BigDecimal(2));
        avocado.setDescription("Avocado");
        avocado.setUop(piece);

        //fresh cilantro
        Ingredient freshCilantro = new Ingredient();
        freshCilantro.setAmount(new BigDecimal(0.1));
        freshCilantro.setDescription("Fresh Cilantro");
        freshCilantro.setUop(ounce);

        //lime
        Ingredient lime = new Ingredient();
        lime.setAmount(new BigDecimal(0.5));
        lime.setDescription("Lime");
        lime.setUop(piece);

        //red onion
        Ingredient redOnion = new Ingredient();
        redOnion.setAmount(new BigDecimal(0.25));
        redOnion.setDescription("Red Onion");
        redOnion.setUop(piece);

        //serrano chili
        Ingredient serranoChiles = new Ingredient();
        serranoChiles.setAmount(new BigDecimal(2));
        serranoChiles.setDescription("Serrano Chili");
        serranoChiles.setUop(piece);

        //tomato
        Ingredient tomato = new Ingredient();
        tomato.setAmount(new BigDecimal(0.5));
        tomato.setDescription("Tomato");
        tomato.setUop(piece);

        //tortilla tortillaChips
        Ingredient tortillaChips = new Ingredient();
        tortillaChips.setAmount(new BigDecimal(0.75));
        tortillaChips.setDescription("Tortilla Chips");
        tortillaChips.setUop(pound);

        //black peppercorns
        Ingredient blackPeppercorns = new Ingredient();
        blackPeppercorns.setAmount(new BigDecimal(0.1));
        blackPeppercorns.setDescription("Black Peppercorns");
        blackPeppercorns.setUop(ounce);

        //salt
        Ingredient salt = new Ingredient();
        salt.setAmount(new BigDecimal(0.1));
        salt.setDescription("Salt");
        salt.setUop(ounce);

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


    private void initRecipes() {
        //check american category
        Optional<Category> americanOpt = categoryRepository.findByDescription("American");

        if (!americanOpt.isPresent()) {
            throw new RuntimeException();
        }

        //check italian category
        Optional<Category> italianOpt = categoryRepository.findByDescription("Italian");

        if (!italianOpt.isPresent()) {
            throw new RuntimeException();
        }

        //check mexican category
        Optional<Category> mexicanOpt = categoryRepository.findByDescription("Mexican");

        if (!mexicanOpt.isPresent()) {
            throw new RuntimeException();
        }

        //check fast food category
        Optional<Category> fastFoodOpt = categoryRepository.findByDescription("Fast food");

        if (!fastFoodOpt.isPresent()) {
            throw new RuntimeException();
        }

        Category mexican = mexicanOpt.get();


        Notes notesForGuacamoleRecipe = new Notes();
        notesForGuacamoleRecipe.setRecipeNotes("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze" +
                " of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or" +
                " spoon it on top of tacos for an easy dinner upgrade.");

        Recipe guacamoleRecipe = new Recipe();
        Set<Ingredient> ingredients = initIngredientsForGuacamole();
        Iterator<Ingredient> itr = ingredients.iterator();

        while (itr.hasNext()) {
            itr.next().setRecipe(guacamoleRecipe);
        }

        Set<Recipe> recipes = new HashSet<Recipe>();
        recipes.add(guacamoleRecipe);
        mexican.setRecipes(recipes);
        guacamoleRecipe.getCategories().add(mexicanOpt.get());
        guacamoleRecipe.setIngredients(ingredients);
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("Veggie salad");
        guacamoleRecipe.setNotes(notesForGuacamoleRecipe);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setUrl("perfect_guacamole");
        guacamoleRecipe.setSource("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setDescription("How to Make Perfect Guacamole");

        recipeRepository.save(guacamoleRecipe);
    }
}
