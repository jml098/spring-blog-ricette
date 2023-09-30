package com.exercise.springblogricette.restcontroller;

import com.exercise.springblogricette.model.Recipe;
import com.exercise.springblogricette.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes/api")
public class RecipeRestController {


    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> recipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

}
