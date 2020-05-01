package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException(" recipe whit ID: " + recipeId + " not found");
        }

        Recipe recipe = recipeOptional.get();

        try {
            byte[] bytes = file.getBytes();
            Byte[] saveBytes = new Byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                saveBytes[i] = bytes[i];
            }

            recipe.setImage(saveBytes);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.error("error parsing image file into array byte");
            e.printStackTrace();
        }
    }
}
