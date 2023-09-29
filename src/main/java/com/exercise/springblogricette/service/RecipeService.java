package com.exercise.springblogricette.service;

import com.exercise.springblogricette.model.Recipe;
import com.exercise.springblogricette.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll(/*Sort.by("creationDate")*/);
    }

    public List<Recipe> filterByParams(Map<String, String> params) {
        List<Recipe> recipes = getAll();

        if (params == null) return recipes;
        String title = params.get("title");
        String ingredients = params.get("ingredients");
        String categoryId = params.get("category");
        String portions = params.get("portions");
        String preparationTime = params.get("preparationTime");

        if (title != null && !title.equals("")) {
            recipes = recipes.stream()
                             .filter(recipe -> recipe.getTitle()
                                                     .toLowerCase()
                                                     .contains(title.toLowerCase()))
                             .toList();
        }

        if (ingredients != null && !ingredients.equals("")) {
            recipes = recipes.stream()
                             .filter(recipe -> recipe.getIngredients()
                                                     .toLowerCase()
                                                     .contains(ingredients.toLowerCase()))
                             .toList();
        }

        if (categoryId != null && !categoryId.equals("")) {
            recipes = recipes.stream().filter(recipe -> recipe.getCategory()
                                                              .getId() == Long.parseLong(
                    categoryId)).toList();
        }

        if (portions != null && !portions.equals("")) {
            recipes = recipes.stream()
                             .filter(recipe -> recipe.getPortions() == Integer.parseInt(
                                     portions))
                             .toList();
        }

        if (preparationTime != null && !preparationTime.equals("")) {
            recipes = recipes.stream().filter(recipe -> {
                LocalTime prepTime = LocalTime.parse(preparationTime);
                return recipe.getPreparationTime().equals(prepTime);
            }).toList();
        }

        return recipes;
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                               .orElseThrow(() -> new ResponseStatusException(
                                       HttpStatus.NOT_FOUND,
                                       "Recipe not found."
                               ));
    }

    public List<Recipe> filterByTitleAndIngredientsAndCategory(
            String title, String ingredients, Long categoryId
    ) {

        return recipeRepository.findAllByTitleContainingIgnoreCaseAndIngredientsContainingIgnoreCaseAndCategory_Id(title,
                                                                                                                   ingredients,
                                                                                                                   categoryId
        );
    }

    public void update(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void insert(Recipe recipe, boolean addDate) {
        if (addDate) recipe.setDate(LocalDate.now());
        recipeRepository.save(recipe);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
