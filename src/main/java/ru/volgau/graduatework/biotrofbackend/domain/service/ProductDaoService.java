package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;

public interface ProductDaoService {

    void save(Product product);

    Product findByProductName(String name);
}
