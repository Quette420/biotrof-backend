package ru.volgau.graduatework.biotrofbackend.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Category;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.domain.service.CategoryDaoService;
import ru.volgau.graduatework.biotrofbackend.domain.service.ProductDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.ProductMapper;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateProductRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateProductQuantityRequest;
import ru.volgau.graduatework.biotrofbackend.utils.StringHelper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductDaoService productDaoService;
    private final CategoryDaoService categoryDaoService;
    private final ProductMapper productMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public List<Product> getAllProductsQuantity() {
        return productDaoService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public void createProduct(@RequestBody CreateProductRequest request) {
        log.info("createProduct({})", request);
        productDaoService.save(productMapper.createProductFromCreateProductRequest(request));
    }

    @Transactional
    @PutMapping("/quantity")
    @PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
    public void updateProductQuantity(@RequestBody UpdateProductQuantityRequest request) {
        log.info("updateProductQuantity({})", request);
        Product product = productDaoService.findByProductName(StringHelper.removeQuantityData(request.getProductName()));
        product.setQuantity(product.getQuantity() == null ? request.getQuantity() : product.getQuantity() + request.getQuantity());
    }
}
