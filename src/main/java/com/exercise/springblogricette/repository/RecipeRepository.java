package com.exercise.springblogricette.repository;

import com.exercise.springblogricette.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByTitleContainingIgnoreCaseAndIngredientsContainingIgnoreCaseAndCategory_Id(
            String title,
            String ingredients,
            Long categoryId
    );

}
