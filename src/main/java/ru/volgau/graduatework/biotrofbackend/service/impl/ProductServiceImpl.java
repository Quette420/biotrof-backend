package ru.volgau.graduatework.biotrofbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Category;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.domain.service.CategoryDaoService;
import ru.volgau.graduatework.biotrofbackend.domain.service.ProductDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.ProductMapper;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDaoService productDaoService;
    private final ProductMapper mapper;
    private final CategoryDaoService categoryDaoService;


    @Override
    public Product findProductOrCreateNew(CreateOrderRequest request) {
        return productDaoService.findByProductName(request.getProductName());
    }

    @Override
    @Transactional
    public void reduceQuantity(Product product, Double quantity) {
        product.setQuantity(product.getQuantity() - quantity);
    }

    @Override
    @Transactional
    public void increaseQuantity(Product product, Double quantity) {
        product.setQuantity(product.getQuantity() + quantity);
    }
}
