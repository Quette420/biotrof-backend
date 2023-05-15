package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Category;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Product;
import ru.volgau.graduatework.biotrofbackend.domain.service.CategoryDaoService;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateProductRequest;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductMapper {

    @Autowired
    protected CategoryDaoService categoryDaoService;

    public void setCategoryDaoService(CategoryDaoService categoryDaoService) {
        this.categoryDaoService = categoryDaoService;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "category", ignore = true)
    public  abstract Product createProductFromCreateOrderRequest(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(getCategory(request.getCategoryName()))")
    public  abstract Product createProductFromCreateProductRequest(CreateProductRequest request);

    protected Category getCategory(String categoryName) {
        Category category = categoryDaoService.findByName(categoryName);
        if(category == null) {
            category = new Category();
            category.setCategoryName(categoryName);
            categoryDaoService.save(category);
        }
        return category;
    }
}
