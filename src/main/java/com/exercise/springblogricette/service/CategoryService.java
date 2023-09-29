package com.exercise.springblogricette.service;

import com.exercise.springblogricette.model.Category;
import com.exercise.springblogricette.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                                 .orElseThrow(() -> new ResponseStatusException(
                                         HttpStatus.NOT_FOUND,
                                         "Category not found."
                                 ));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll(/*Sort.by("creationDate")*/);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void insert(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
