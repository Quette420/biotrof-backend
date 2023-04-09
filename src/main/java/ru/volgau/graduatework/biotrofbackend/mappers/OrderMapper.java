package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.UpdateOrderRequest;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class OrderMapper {

    @Mapping(target = "isShipped", constant = "false")
    @Mapping(target = "stage", constant = "WAITING_FOR_PAYMENT")
    public abstract Order createOrderRequestToOrder(CreateOrderRequest request);

    @Mapping(target = "order.getProduct().getProductName()", expression = "java(notEmptyValidate(request.getProductName(), order.getProductName()))")
    @Mapping(target = "order.getProduct().getCategory()", expression = "java(notEmptyValidate(request.getCategory(), order.getCategory()))")
    @Mapping(target = "wishes", expression = "java(notEmptyValidate(request.getWishes(), order.getWishes()))")
    public abstract void updateOrderRequestToOrder(UpdateOrderRequest request, @MappingTarget Order order);

    protected String notEmptyValidate(String requestField, String orderField) {
        return requestField == null ? orderField : requestField.isEmpty() ? orderField : requestField;
    }
}
