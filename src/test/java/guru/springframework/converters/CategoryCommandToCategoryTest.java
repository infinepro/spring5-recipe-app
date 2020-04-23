package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryCommandToCategoryTest {

    final Long CATEGORY_ID = 11L;
    final String CATEGORY_DESCRIPTION = "Some description";

    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void testConvertCategoryCommandToCategory() {
        //set up data
        CategoryCommand testCategoryCommand = new CategoryCommand()
                .setId(CATEGORY_ID)
                .setDescription(CATEGORY_DESCRIPTION);

        //convert
        Category convertedCategory = categoryCommandToCategory.convert(testCategoryCommand);

        //check
        Assertions.assertEquals(convertedCategory.getId(), testCategoryCommand.getId());
        Assertions.assertEquals(convertedCategory.getDescription(), testCategoryCommand.getDescription());

    }
}