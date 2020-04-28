package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientService {

    @Transactional
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    IngredientCommand getIngredientById(Long id);
}

