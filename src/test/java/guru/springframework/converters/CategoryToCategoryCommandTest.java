package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryToCategoryCommandTest {

    final Long CATEGORY_ID = 11L;
    final String CATEGORY_DESCRIPTION = "Some description";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    void testConvertCategoryToCategoryCommand() {
        //set up data
        Category testCategory = new Category()
                .setId(CATEGORY_ID)
                .setDescription(CATEGORY_DESCRIPTION);

        //convert
        CategoryCommand convertedCategoryCommand = categoryToCategoryCommand.convert(testCategory);

        //check
        Assertions.assertEquals(testCategory.getId(), convertedCategoryCommand.getId());
        Assertions.assertEquals(testCategory.getDescription(), convertedCategoryCommand.getDescription());

    }
}