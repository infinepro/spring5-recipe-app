package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryToCategoryCommand implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category convertObject) {

        if (convertObject == null) {
            return null;
        }

        return new CategoryCommand()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription());

    }
}
