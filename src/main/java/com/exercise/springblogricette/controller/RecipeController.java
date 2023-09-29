package com.exercise.springblogricette.controller;

import com.exercise.springblogricette.model.Recipe;
import com.exercise.springblogricette.service.CategoryService;
import com.exercise.springblogricette.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(
            RecipeService recipeService, CategoryService categoryService
    ) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String recipes(Model model) {
        model.addAttribute("recipes", recipeService.getAll());
        return "recipes/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.getAll());
        return "recipes/form";
    }


    @PostMapping("/create")
    public String create(
            Model model,
            @Valid @ModelAttribute Recipe recipe,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "recipes/form";
        }

        recipeService.insert(recipe, true);
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("recipe", recipeService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "recipes/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            Model model,
            @PathVariable Long id,
            @Valid @ModelAttribute Recipe recipe,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "recipes/form";
        }

        recipeService.update(recipe);
        return "redirect:/recipes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        recipeService.deleteById(id);
        return "redirect:/recipes";
    }

}
