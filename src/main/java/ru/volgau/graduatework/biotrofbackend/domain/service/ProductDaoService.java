package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import java.util.List;

public interface ProductDaoService {

    void save(Product product);

    Product findByProductName(String name);

    List<Product> getAll();
}
