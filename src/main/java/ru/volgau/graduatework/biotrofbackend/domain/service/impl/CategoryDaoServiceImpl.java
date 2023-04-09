package ru.volgau.graduatework.biotrofbackend.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Category;
import ru.volgau.graduatework.biotrofbackend.domain.repository.CategoryRepository;
import ru.volgau.graduatework.biotrofbackend.domain.service.CategoryDaoService;

@Service
@RequiredArgsConstructor
public class CategoryDaoServiceImpl implements CategoryDaoService {

    private final CategoryRepository repository;

    @Override
    public void save(Category category) {
        repository.save(category);
    }

    @Override
    public Category findByName(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }
}
