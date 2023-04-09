package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Category;

public interface CategoryDaoService {

    void save(Category category);

    Category findByName(String categoryName);
}
