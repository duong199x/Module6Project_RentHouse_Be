package vn.codegym.houserental.service.impl;

import vn.codegym.houserental.model.Category;
import vn.codegym.houserental.repository.CategoryRepository;
import vn.codegym.houserental.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Iterable<Category> findAllByDeleteFlag(boolean deleteFlag) {
        return categoryRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<Category> findOneById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            category.get().setDeleteFlag(true);
            categoryRepository.save(category.get());
        }
    }
}
