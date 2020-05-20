package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Profile({"prod", "dev"})
public class BootstrapMySql implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;


    public BootstrapMySql(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (categoryRepository.count() == 0L) {
            categoryRepository.saveAll(getStandartCategories());
        }

        if (unitOfMeasureRepository.count() == 0L) {
            unitOfMeasureRepository.saveAll(getStandartUnitOfMeasure());
        }
    }

    private List<Category> getStandartCategories() {
        ArrayList<Category> list = new ArrayList<>();

        list.add(new Category().setDescription("American"));
        list.add(new Category().setDescription("Italian"));
        list.add(new Category().setDescription("Mexican"));
        list.add(new Category().setDescription("Fast food"));

        return list;
    }


    private List<UnitOfMeasure> getStandartUnitOfMeasure() {
        ArrayList<UnitOfMeasure> list = new ArrayList<>();

        list.add(new UnitOfMeasure().setDescription("teaspoon"));
        list.add(new UnitOfMeasure().setDescription("tablespoon"));
        list.add(new UnitOfMeasure().setDescription("cup"));
        list.add(new UnitOfMeasure().setDescription("pinch"));
        list.add(new UnitOfMeasure().setDescription("ounce"));
        list.add(new UnitOfMeasure().setDescription("pound"));
        list.add(new UnitOfMeasure().setDescription("piece"));

        return list;
    }
}
