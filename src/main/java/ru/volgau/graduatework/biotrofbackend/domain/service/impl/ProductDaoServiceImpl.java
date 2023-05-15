package ru.volgau.graduatework.biotrofbackend.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.domain.repository.ProductRepository;
import ru.volgau.graduatework.biotrofbackend.domain.service.ProductDaoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDaoServiceImpl implements ProductDaoService {

    private final ProductRepository repository;

    @Override
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    public Product findByProductName(String name) {
        return repository.findByProductName(name);
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }
}
