package com.exercise.springblogricette.service;

import com.exercise.springblogricette.model.Recipe;
import com.exercise.springblogricette.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll(/*Sort.by("creationDate")*/);
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

        return recipeRepository.findAllByTitleContainingIgnoreCaseAndIngredientsContainingIgnoreCaseAndCategory_Id(
                title,
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
