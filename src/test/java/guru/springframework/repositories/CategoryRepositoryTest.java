package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void findByDescriptionTest () {
        Optional<Category> testCategory = categoryRepository.findByDescription("American");
        Assertions.assertEquals("American", testCategory.get().getDescription());
    }
}