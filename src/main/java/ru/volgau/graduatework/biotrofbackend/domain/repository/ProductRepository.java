package ru.volgau.graduatework.biotrofbackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String productName);
}
