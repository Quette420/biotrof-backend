package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Order;
import ru.volgau.graduatework.biotrofbackend.model.dto.OrderReportDto;
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

    @Mapping(target = "productName", source = "order.product.productName")
    @Mapping(target = "category", source = "order.product.category.categoryName")
    @Mapping(target = "clientFirstName", source = "order.client.firstName")
    @Mapping(target = "clientLastName", source = "order.client.lastName")
    @Mapping(target = "clientMiddleName", source = "order.client.middleName")
    @Mapping(target = "clientNumber", source = "order.client.phoneNumber")
    @Mapping(target = "address", source = "order.client.address")
    public abstract OrderReportDto toOrderReportDto(Order order);

    protected String notEmptyValidate(String requestField, String orderField) {
        return requestField == null ? orderField : requestField.isEmpty() ? orderField : requestField;
    }
}
