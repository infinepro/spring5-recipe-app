package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        return new Category()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription());

    }
}
