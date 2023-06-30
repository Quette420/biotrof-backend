package ru.volgau.graduatework.biotrofbackend.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;

public interface ProductService {

    Product findProductOrCreateNew(CreateOrderRequest request);

    void reduceQuantity(Product product, Double quantity);

    void increaseQuantity(Product product, Double quantity);
}
