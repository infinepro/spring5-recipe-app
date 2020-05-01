package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{id}/image/new")
    public String getUploadImageForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));

        return "recipe/image/image-form";
    }

    @PostMapping("recipe/{id}/image/new")
    public String uploadImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {
        imageService.saveImageFile(id, file);

        return "redirect:/recipe/show/" + id;
    }

    @GetMapping("recipe/{id}/recipe-image")
    public void getImageFromDb(@PathVariable Long id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(id);

        byte[] primBytes = new byte[recipeCommand.getImage().length];
        Byte[] bytes = recipeCommand.getImage();

        for (int i = 0; i < primBytes.length; i++) {
            primBytes[i] = bytes[i];
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(primBytes);
        IOUtils.copy(is, response.getOutputStream());

    }


}
