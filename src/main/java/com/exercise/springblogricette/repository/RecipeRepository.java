package com.exercise.springblogricette.repository;

import com.exercise.springblogricette.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {}
